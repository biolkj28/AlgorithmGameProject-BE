package com.seventeam.algoritmgameproject.web.socketServer.repository;


import com.seventeam.algoritmgameproject.domain.model.Question;
import com.seventeam.algoritmgameproject.web.dto.UserGameInfo;
import com.seventeam.algoritmgameproject.web.service.compilerService.Language;
import com.seventeam.algoritmgameproject.web.socketServer.model.GameRoom;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.*;
import org.springframework.stereotype.Repository;

import javax.annotation.PostConstruct;
import java.util.*;

@RequiredArgsConstructor
@Repository
@Slf4j
public class GameRoomRepository {

    // 채팅룸에 입장한 클라이언트의 sessionId와 채팅룸 id를 맵핑한 정보 저장
    public static final String ROOM_READY = "ROOM_READY";

    private final RedisTemplate<String, Object> redisTemplate;

    private HashOperations<String, String, GameRoom> hashOpsGameRoom;
    private HashOperations<String, String, String> hashOpsEnterInfo;

    @PostConstruct
    void init() {
        hashOpsGameRoom = redisTemplate.opsForHash();
        hashOpsEnterInfo = redisTemplate.opsForHash();
    }

    public List<GameRoom> findIsEnterAndSelectedLangAndLevelRooms(String server) {
        //문제 block

        List<GameRoom> values = hashOpsGameRoom.values(server);
        List<GameRoom> isEnterRooms = new ArrayList<>();
        for (GameRoom value : values) {
            if (value.isEnter()) {
                isEnterRooms.add(value);
            }
        }
        return isEnterRooms;
    }

    public GameRoom findRoomById(String server, String roomId) {
        return hashOpsGameRoom.get(server, roomId);
    }

    //게임방 생성
    public GameRoom createGameRoom(Language language, Question question, UserGameInfo creator) {

        GameRoom gameRoom = GameRoom.builder()
                .language(language.name())
                .questionLevel(question.getLevel().name())
                .questionId(question.getId())
                .questionTitle(question.getTitle())
                .question(question.getQuestion())
                .startTemplate(question.getTemplates().get(language.getValue()))
                .creatorGameInfo(creator)
                .build();
        gameRoom.questionBlock();

        hashOpsGameRoom.put(
                language.name() + question.getLevel().name(),
                gameRoom.getRoomId(),
                gameRoom
        );

        return gameRoom;
    }

    public UserGameInfo enterGameRoom(String server, String roomId) {
        //UserInfo로 변경
        GameRoom room = findRoomById(server, roomId);
        //해당 방 입장 처리
        room.setEnter();
        hashOpsGameRoom.putIfAbsent(
                server,
                room.getRoomId(),
                room
        );
        return room.getCreatorGameInfo();
    }

    public void ExitGameRoom(String server, String roomId) {
        if (hashOpsEnterInfo.size(roomId) == 0) {
            hashOpsGameRoom.delete(server, roomId);
        } else {
            GameRoom room = findRoomById(server, roomId);
            room.setExit();
            hashOpsGameRoom.putIfAbsent(server, roomId, room);
        }
    }

    public void ready(String roomId) {
        if (Boolean.FALSE.equals(redisTemplate.hasKey(ROOM_READY + roomId))) {
            redisTemplate.opsForValue().set(ROOM_READY + roomId, false);
        } else {
            redisTemplate.opsForValue().set(ROOM_READY + roomId, true);
        }
    }

    public boolean readyCheck(String roomId) {
        boolean allReady = false;
        Object o = redisTemplate.opsForValue().get(ROOM_READY + roomId);
        if (o != null) {
            if ((boolean)o) {
                allReady = true;
            }
        }
        return allReady;
    }

    public void deleteReady(String roomId) {
        redisTemplate.delete(ROOM_READY + roomId);
    }

    public String findOthersSession(String roomId,String sessionId){
        Set<String> keys = hashOpsEnterInfo.keys(roomId);
        String session = null;
        for (String key : keys) {
            log.info("sessions:{}",key);
            if(!sessionId.equals(key)){
               session =  hashOpsEnterInfo.get(roomId, key);
            }
        }
        return session;
    }
    public void saveEnterSession(String roomId, String sessionId, String userId) {
        redisTemplate.opsForValue().setIfAbsent(sessionId, roomId);
        hashOpsEnterInfo.putIfAbsent(roomId, sessionId, userId);
    }
    public String findSessionToRoom(String session){
        return (String) redisTemplate.opsForValue().get(session);
    }
    public void deleteEnterSession(String sessionId) {
        String roomId = findSessionToRoom(sessionId);
        redisTemplate.delete(sessionId);
        log.info("room: {}",roomId);
        hashOpsEnterInfo.delete(roomId, sessionId);
        log.info("세션 {}, 삭제 완료",sessionId);
    }

}
