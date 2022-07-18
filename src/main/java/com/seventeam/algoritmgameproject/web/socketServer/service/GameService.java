package com.seventeam.algoritmgameproject.web.socketServer.service;

import com.seventeam.algoritmgameproject.domain.QuestionLevel;
import com.seventeam.algoritmgameproject.domain.model.Question;
import com.seventeam.algoritmgameproject.domain.repository.QuestionDslRepository;
import com.seventeam.algoritmgameproject.web.dto.UserGameInfo;
import com.seventeam.algoritmgameproject.web.service.compilerService.Language;
import com.seventeam.algoritmgameproject.web.socketServer.Dto.EnterRoomRequestDto;
import com.seventeam.algoritmgameproject.web.socketServer.model.GameMessage;
import com.seventeam.algoritmgameproject.web.socketServer.model.GameRoom;
import com.seventeam.algoritmgameproject.web.socketServer.model.ReadyMessage;
import com.seventeam.algoritmgameproject.web.socketServer.repository.GameRoomRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.listener.ChannelTopic;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class GameService {
    private final GameRoomRepository gameRoomRepository;
    private final QuestionDslRepository questionDslRepository;
    private final ChannelTopic channelTopic;
    private final RedisTemplate<String, Object> redisTemplate;
    private final GameServiceUtil util;
    private final SimpMessagingTemplate messagingTemplate;

    public List<GameRoom> findRooms(int langIdx, int levelIdx) {
        return gameRoomRepository.findIsEnterAndSelectedLangAndLevelRooms(util.findServerName(langIdx, levelIdx));
    }

    public GameRoom findRoom(int langIdx, int levelIdx, String roomId) {
        String server = util.findServerName(langIdx, levelIdx);
        GameRoom room = gameRoomRepository.findRoomById(server, roomId);

        if (room == null) {
            throw new NullPointerException("해당 방은 존재하지 않습니다.");
        }
        return room;
    }

    // 방생성과 동시에 해당 난이도, 언어에 맞는 랜덤 문제 할당
    public GameRoom createRoom(int langIdx, int questionLevelIdx) {

        Language language = util.getSelectedLang(langIdx);
        QuestionLevel level = util.getSelectedLevel(questionLevelIdx);

        UserGameInfo userGameInfo = new UserGameInfo("ljc4602", "No", 10L, 2L);
        Question question = questionDslRepository.findRandomQuestionLevel(level);

        return gameRoomRepository.createGameRoom(language, question, userGameInfo);
    }

    public void sendGameCode(GameMessage message, String sessionId) {
        String othersSession = gameRoomRepository.findOthersSession(message.getRoomId(), sessionId);
        log.info("sender:{}",sessionId);
        log.info("to:{}",othersSession);
        message.setTo(othersSession);
        redisTemplate.convertAndSend(channelTopic.getTopic(), message);
    }

    //입장 가능 확인
    public boolean isEnter(String server, String roomId) {
        GameRoom room = gameRoomRepository.findRoomById(server, roomId);
        return room.isEnter();
    }

    //방 입장
    public UserGameInfo enterRoom(EnterRoomRequestDto dto) {
        return gameRoomRepository.enterGameRoom(dto.getServer(), dto.getRoomId());
    }

    //준비 메시지 처리
    public void ready(ReadyMessage message) {
        gameRoomRepository.ready(message.getRoomId());

        if (gameRoomRepository.readyCheck(message.getRoomId())) {

            GameRoom room = gameRoomRepository.findRoomById(message.getServer(), message.getRoomId());
            message.setTitle(room.getQuestionTitle());
            message.setQuestion(room.getQuestion());
            message.setTemplate(room.getStartTemplate());

            //준비 데이터 삭제
            gameRoomRepository.deleteReady(message.getRoomId());
            redisTemplate.convertAndSend(channelTopic.getTopic(), message);
        }
    }
}
