package com.seventeam.algoritmgameproject.web.socketServer.service;

import com.seventeam.algoritmgameproject.domain.QuestionLevel;
import com.seventeam.algoritmgameproject.domain.model.Question;
import com.seventeam.algoritmgameproject.domain.model.User;
import com.seventeam.algoritmgameproject.domain.repository.QuestionDslRepository;
import com.seventeam.algoritmgameproject.security.repository.UserRepository;
import com.seventeam.algoritmgameproject.web.socketServer.model.Dto.FailMessageAndToDto;
import com.seventeam.algoritmgameproject.web.socketServer.model.Dto.LoseMessageDto;
import com.seventeam.algoritmgameproject.web.socketServer.model.Dto.UserAndRoomIdDto;
import com.seventeam.algoritmgameproject.web.socketServer.model.*;
import com.seventeam.algoritmgameproject.web.service.compilerService.Language;
import com.seventeam.algoritmgameproject.web.socketServer.model.Dto.EnterAndExitRoomRequestDto;
import com.seventeam.algoritmgameproject.web.socketServer.repository.GameRoomRepository;
import com.seventeam.algoritmgameproject.web.socketServer.repository.GameSessionRepository;
import com.seventeam.algoritmgameproject.web.socketServer.repository.ReadyRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.listener.ChannelTopic;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
        Question question = questionDslRepository.findRandomQuestionLevel(level);
        GameRoom gameRoom = gameRoomRepository.createGameRoom(language, question, userGameInfo);
        gameRoom.questionBlock();

        //세션 저장
        sessionRepository.saveEnterSession(gameRoom.getRoomId(), user.getUserId(), GameSessionRepository.CREATOR);

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

        GameRoom room = gameRoomRepository.findRoomById(dto.getServer(), dto.getRoomId());
        UserGameInfo creator = room.getCreatorGameInfo();
        if (Optional.ofNullable(creator).isPresent()) {
            gameRoomRepository.enterAndExitGameRoom(room, true);
            // 입장자 세션 저장, 전송 queue 주소 구독 필수
            sessionRepository.saveEnterSession(dto.getRoomId(), GameSessionRepository.PARTICIPANT, user.getUserId());
            return creator;

        }
        return null;
    }

    @Override
    public void exitRoom(String server, String roomId, User user) {

        String role = sessionRepository.getRole(roomId, user.getUserId());
        GameRoom roomById = gameRoomRepository.findRoomById(server, roomId);

        if (role == null) {
            throw new NullPointerException("참여자가 아닙니다");
        }
        if (role.equals(GameSessionRepository.PARTICIPANT)) {

            sessionRepository.deleteSession(roomId, user.getUserId());

        } else if (role.equals(GameSessionRepository.CREATOR)) {
            sessionRepository.deleteSession(roomId, user.getUserId());

            Long cnt = sessionRepository.roomEnterCnt(roomId);
            if (cnt == 0) {
                gameRoomRepository.deleteRoom(server, roomId);
                gameRoomRepository.deleteServerRoomData(roomId);

            } else {

                String othersSession = sessionRepository.findOthersSession(roomId, user.getUserId());

                Optional<User> byUserId = userRepository.findByUserId(othersSession);
                User otherUser = byUserId.orElseThrow(() -> new IllegalArgumentException("없는 사용자 입니다."));
                UserGameInfo otherUserGameInfo = userToUserInfo(otherUser);

                sessionRepository.upgradeRole(roomId, othersSession);
                gameRoomRepository.changeCreator(roomById, otherUserGameInfo);

            }

        }
        gameRoomRepository.enterAndExitGameRoom(roomById, false);

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
        String role = sessionRepository.getRole(roomId, username);
        return role.equals(GameSessionRepository.PARTICIPANT);
    }

    @Override
    public void sendToMyUserInfo(String roomId, String username) {
        Optional<User> byUserId = userRepository.findByUserId(username);
        User myInfo = byUserId.orElseThrow(() -> new IllegalArgumentException("유저 정보가 없습니다."));
        UserAndRoomIdDto userAndRoomIdDto = new UserAndRoomIdDto(roomId, userToUserInfo(myInfo));
        redisTemplate.convertAndSend(channelTopic.getTopic(), userAndRoomIdDto);
    }

    @Override
    public void sendLoseMessage(String roomId, String username) {
        String otherUserId = sessionRepository.findOthersSession(roomId, username);
        LoseMessage msg = new LoseMessage();
        redisTemplate.convertAndSend(channelTopic.getTopic(), new LoseMessageDto(roomId, msg, otherUserId));
    }

    @Override
    public void sendOpFailMessage(String roomId, String username) {
        String otherUserId = sessionRepository.findOthersSession(roomId, username);
        OpFailMessage msg = new OpFailMessage();
        FailMessageAndToDto dto = new FailMessageAndToDto(roomId, msg, otherUserId);
        redisTemplate.convertAndSend(channelTopic.getTopic(), dto);
    }

    @Override
    public void disconnectEvent(String roomId, String username) {
        sessionRepository.deleteSession(roomId, username);
        String role = sessionRepository.getRole(roomId, username);
        String server = gameRoomRepository.findServer(roomId);
        if (sessionRepository.roomEnterCnt(roomId) == 1) {
            GameRoom roomById = gameRoomRepository.findRoomById(server, roomId);
            if (role.equals(GameSessionRepository.PARTICIPANT)) {
                gameRoomRepository.enterAndExitGameRoom(roomById, false);
            } else {
                String othersSession = sessionRepository.findOthersSession(roomId, username);
                Optional<User> byUserId = userRepository.findByUserId(othersSession);
                User otherUser = byUserId.orElseThrow(() -> new IllegalArgumentException("없는 사용자 입니다."));
                UserGameInfo otherUserGameInfo = userToUserInfo(otherUser);

                sessionRepository.upgradeRole(roomId, othersSession);
                gameRoomRepository.changeCreator(roomById, otherUserGameInfo);
                gameRoomRepository.enterAndExitGameRoom(roomById, false);
            }

        }

        if (sessionRepository.roomEnterCnt(roomId) == 0) {
            gameRoomRepository.deleteRoom(server, roomId);
            gameRoomRepository.deleteServerRoomData(roomId);
        }

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
