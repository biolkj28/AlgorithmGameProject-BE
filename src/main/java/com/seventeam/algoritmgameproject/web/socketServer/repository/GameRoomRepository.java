package com.seventeam.algoritmgameproject.web.socketServer.repository;


import com.seventeam.algoritmgameproject.domain.model.Question;
import com.seventeam.algoritmgameproject.web.socketServer.model.UserGameInfo;
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


    private static final String FIND_SERVER="FIND_SERVER";
    private final RedisTemplate<String, Object> redisTemplate;
    private HashOperations<String, String, GameRoom> hashOpsGameRoom;

    @PostConstruct
    void init() {
        hashOpsGameRoom = redisTemplate.opsForHash();
    }

    public List<GameRoom> findIsEnterAndSelectedLangAndLevelRooms(String server) {

        List<GameRoom> values = hashOpsGameRoom.values(server);
        List<GameRoom> isEnterRooms = new ArrayList<>();
        for (GameRoom value : values) {
            if (value.isEnter()) {
                value.questionBlock();
                isEnterRooms.add(value);
            }
        }
        return isEnterRooms;
    }

    public GameRoom findRoomById(String server, String roomId) {
        return hashOpsGameRoom.get(server, roomId);
    }

    public String findServer(String roomId) {
        return Objects.requireNonNull(redisTemplate.opsForValue().get(FIND_SERVER + roomId)).toString();
    }
    public void deleteServerRoomData(String roomId){
        redisTemplate.delete(FIND_SERVER + roomId);
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
        redisTemplate.opsForValue().set(FIND_SERVER + gameRoom.getRoomId(), gameRoom.getServer());
        hashOpsGameRoom.put(
                language.name() + question.getLevel().name(),
                gameRoom.getRoomId(),
                gameRoom
        );

        return gameRoom;
    }

    public void deleteRoom(String server, String roomId) {
        hashOpsGameRoom.delete(server, roomId);
    }

    public void changeCreator(GameRoom room, UserGameInfo userGameInfo) {
        room.changeCreator(userGameInfo);
        hashOpsGameRoom.putIfAbsent(room.getServer(), room.getRoomId(), room);
    }

    public void enterAndExitGameRoom(GameRoom room, boolean enter) {
        if (enter) {
            room.setEnter();
            hashOpsGameRoom.putIfAbsent(
                    room.getServer(),
                    room.getRoomId(),
                    room
            );
        } else {
            room.setExit();
            hashOpsGameRoom.putIfAbsent(
                    room.getServer(),
                    room.getRoomId(),
                    room
            );
        }
    }


    //세션 관리 CRUD


    //수정 완료


}
