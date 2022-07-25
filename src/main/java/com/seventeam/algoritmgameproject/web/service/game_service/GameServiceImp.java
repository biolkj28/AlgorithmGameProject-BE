package com.seventeam.algoritmgameproject.web.service.game_service;

import com.seventeam.algoritmgameproject.domain.QuestionLevel;
import com.seventeam.algoritmgameproject.domain.model.game.*;
import com.seventeam.algoritmgameproject.domain.model.questions.Question;
import com.seventeam.algoritmgameproject.domain.model.login.User;
import com.seventeam.algoritmgameproject.web.repository.questions_repository.QuestionDslRepository;
import com.seventeam.algoritmgameproject.web.repository.UserRepository;
import com.seventeam.algoritmgameproject.web.dto.game_dto.UserAndRoomIdDto;
import com.seventeam.algoritmgameproject.web.service.compiler_service.Language;
import com.seventeam.algoritmgameproject.web.dto.game_dto.EnterAndExitRoomRequestDto;
import com.seventeam.algoritmgameproject.web.repository.game_repository.GameRoomRepository;
import com.seventeam.algoritmgameproject.web.repository.game_repository.GameSessionRepository;
import com.seventeam.algoritmgameproject.web.repository.game_repository.ReadyRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.listener.ChannelTopic;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class GameServiceImp implements GameService {
    private final GameRoomRepository gameRoomRepository;
    private final GameSessionRepository sessionRepository;
    private final UserRepository userRepository;
    private final ReadyRepository readyRepository;
    private final QuestionDslRepository questionDslRepository;
    private final ChannelTopic channelTopic;
    private final RedisTemplate<String, Object> redisTemplate;
    private final GameServiceUtil util;

    @Override
    public List<GameRoom> findRooms(int langIdx, int levelIdx) {
        return gameRoomRepository.findIsEnterAndSelectedLangAndLevelRooms(util.findServerName(langIdx, levelIdx));
    }

    // 방생성과 동시에 해당 난이도, 언어에 맞는 랜덤 문제 할당
    @Override
    @Transactional
    public GameRoom createRoom(int langIdx, int questionLevelIdx, User user) {
        log.info("{} 님 방 생성", user.getUserId());

        Language language = util.getSelectedLang(langIdx);
        QuestionLevel level = util.getSelectedLevel(questionLevelIdx);
        UserGameInfo userGameInfo = UserGameInfo.builder()
                .playerName(user.getUserId())
                .profileUrl(user.getAvatarUrl())
                .winCnt(user.getWinCnt())
                .loseCnt(user.getLoseCnt())
                .build();

        List<Long> idListByLevel = questionDslRepository.findIdListByLevel(level);

        if (level.equals(QuestionLevel.HARD)) {
            idListByLevel.remove(idListByLevel.size() - 1);
        }

        Collections.shuffle(idListByLevel);
        Question question = questionDslRepository.findOneQuestionByLevel(level, idListByLevel.get(0));
        GameRoom gameRoom = gameRoomRepository.createGameRoom(language, question, userGameInfo);
        gameRoom.questionBlock();

        //세션 저장
        sessionRepository.saveEnterSession(gameRoom.getRoomId(), user.getUserId(), GameSessionRepository.CREATOR);
        //추후 지우기
        String role = sessionRepository.getRole(gameRoom.getRoomId(), user.getUserId());
        log.info("입장자 권한 부여:{}",role);
        //
        sessionRepository.saveRoomIdBySession(user.getUserId(), gameRoom.getRoomId());
        return gameRoom;

    }

    @Override
    public void sendGameCode(GameMessage message) {

        String othersSession = sessionRepository.findOthersSession(message.getRoomId(), message.getSender());
        log.info("sender:{}", message.getSender());
        log.info("to:{}", othersSession);
        message.setTo(othersSession);
        redisTemplate.convertAndSend(channelTopic.getTopic(), message);

    }

    //입장 가능 확인
    @Override
    public boolean isEnter(String server, String roomId) {
        GameRoom room = gameRoomRepository.findRoomById(server, roomId);
        return room.isEnter();
    }

    //방 입장, 본인 정보 전송, userDetailPrincipal 추가
    @Override
    public UserGameInfo enterRoom(EnterAndExitRoomRequestDto dto, User user) {
        log.info("방 입장:{}", dto.getRoomId());
        log.info("방 입장:{}", user.getUserId());
        GameRoom room = gameRoomRepository.findRoomById(dto.getServer(), dto.getRoomId());
        UserGameInfo creator = room.getCreatorGameInfo();
        if (Optional.ofNullable(creator).isPresent()) {
            log.info("입장 인원:{}", user.getUserId());
            gameRoomRepository.enterAndExitGameRoom(room, true);
            // 입장자 세션 저장, 전송 queue 주소 구독 필수

            sessionRepository.saveEnterSession(dto.getRoomId(), user.getUserId(), GameSessionRepository.PARTICIPANT);
            //추후 지우기
            String role = sessionRepository.getRole(dto.getRoomId(), user.getUserId());
            log.info("입장자 권한 부여:{}",role);
            //
            sessionRepository.saveRoomIdBySession(user.getUserId(), dto.getRoomId());
            return creator;

        }
        return null;
    }

    @Override
    public void exitRoom(String server, String roomId, User user) {

        //권한
        String role = sessionRepository.getRole(roomId, user.getUserId());
        log.info("퇴장 이벤트 사용 :{}, ROLE:{}", user.getUserId(), role);
        GameRoom roomById = gameRoomRepository.findRoomById(server, roomId);
        exitEvent(roomById,user.getUserId(),role);
    }
    public void  exitEvent(GameRoom room, String username, String role){

        if (role == null) {
            throw new NullPointerException("참여자가 아닙니다");
        }

        //방 정보에서 입장 가능으로 변경
        gameRoomRepository.enterAndExitGameRoom(room, false);
        // 방 기준 세션 정보 삭제
        sessionRepository.deleteSession(room.getRoomId(), username);
        //세션에 저장된 방 정보 삭제
        sessionRepository.deleteRoomIdBySession(username);
        Long cnt = sessionRepository.roomEnterCnt(room.getRoomId());
        if (role.equals(GameSessionRepository.CREATOR)) {

            log.info("잔여 인원:{}", cnt);

            if(cnt == 1) {

                log.info("방 참여자 권한 상승 진행");
                String othersSession = sessionRepository.findOthersSession(room.getRoomId(), username);
                Optional<User> byUserId = userRepository.findByUserId(othersSession);
                User otherUser = byUserId.orElseThrow(() -> new IllegalArgumentException("없는 사용자 입니다."));
                UserGameInfo otherUserGameInfo = userToUserInfo(otherUser);

                sessionRepository.upgradeRole(room.getRoomId(), othersSession);
                gameRoomRepository.changeCreator(room, otherUserGameInfo);

            }else if (cnt == 0) {

                log.info("방 삭제 로직 진행");
                gameRoomRepository.deleteRoom(room.getServer(), room.getRoomId());
                gameRoomRepository.deleteServerRoomData(room.getRoomId());

            }

        }
    }
    //준비 메시지 처리
    @Override
    public void ready(ReadyMessage message) {

        readyRepository.ready(message.getRoomId());

        if (readyRepository.readyCheck(message.getRoomId())) {

            GameRoom room = gameRoomRepository.findRoomById(message.getServer(), message.getRoomId());
            message.setTitle(room.getQuestionTitle());
            message.setQuestion(room.getQuestion());
            message.setTemplate(room.getStartTemplate());

            //준비 데이터 삭제
            readyRepository.deleteReady(message.getRoomId());
            redisTemplate.convertAndSend(channelTopic.getTopic(), message);
        }

    }

    @Override
    public Boolean isParticipant(String roomId, String username) {
        log.info("방:{},입장:{}", roomId, username);
        String role = sessionRepository.getRole(roomId, username);
        log.info("역할:{}", role);
        return role.equals(GameSessionRepository.PARTICIPANT);
    }

    @Override
    public void sendToMyUserInfo(String roomId, String username) {
        log.info("roomId: {}", roomId);
        log.info("username: {}", username);
        Optional<User> byUserId = userRepository.findByUserId(username);
        User myInfo = byUserId.orElseThrow(() -> new IllegalArgumentException("유저 정보가 없습니다."));
        String othersSession = sessionRepository.findOthersSession(roomId, username);
        UserAndRoomIdDto userAndRoomIdDto = new UserAndRoomIdDto(roomId, userToUserInfo(myInfo), othersSession);

        redisTemplate.convertAndSend(channelTopic.getTopic(), userAndRoomIdDto);
    }
    @Override
    public void disconnectEvent(String username) {
        String roomId = sessionRepository.findRoomIdBySession(username);
        log.info("방 퇴장 처리 중:{}", roomId);
        String role = sessionRepository.getRole(roomId, username);
        String server = gameRoomRepository.findServer(roomId);
        GameRoom roomById = gameRoomRepository.findRoomById(server, roomId);
        exitEvent(roomById,username,role);
    }

    @Override
    public UserGameInfo userToUserInfo(User user) {
        return UserGameInfo.builder()
                .playerName(user.getUserId())
                .profileUrl(user.getAvatarUrl())
                .winCnt(user.getWinCnt())
                .loseCnt(user.getLoseCnt())
                .build();
    }
}
