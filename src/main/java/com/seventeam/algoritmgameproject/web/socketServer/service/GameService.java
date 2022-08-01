package com.seventeam.algoritmgameproject.web.socketServer.service;

import com.seventeam.algoritmgameproject.domain.model.User;
import com.seventeam.algoritmgameproject.web.socketServer.model.Dto.EnterAndExitRoomRequestDto;
import com.seventeam.algoritmgameproject.web.socketServer.model.GameMessage;
import com.seventeam.algoritmgameproject.web.socketServer.model.GameRoom;
import com.seventeam.algoritmgameproject.web.socketServer.model.ReadyMessage;
import com.seventeam.algoritmgameproject.web.socketServer.model.UserGameInfo;

import java.util.List;

public interface GameService {
    List<GameRoom> findRooms(int langIdx, int levelIdx);

    // 방생성과 동시에 해당 난이도, 언어에 맞는 랜덤 문제 할당
    GameRoom createRoom(int langIdx, int questionLevelIdx, User user);

    void sendGameCode(GameMessage message);

    //입장 가능 확인
    boolean isEnter(String server, String roomId);

    //방 입장, 본인 정보 전송, userDetailPrincipal 추가
    UserGameInfo enterRoom(EnterAndExitRoomRequestDto dto, User user);

    void exitRoom(String server, String roomId, User user);

    //준비 메시지 처리
    void ready(ReadyMessage message);

    Boolean isParticipant(String roomId, String username);

    void sendToMyUserInfo(String roomId, String username);

    void sendLoseMessage(String roomId, String username);

    void sendOpFailMessage(String roomId, String username);

    void disconnectEvent(String username);

    UserGameInfo userToUserInfo(User user);
}
