package com.seventeam.algoritmgameproject.web.service.game_service;

import com.seventeam.algoritmgameproject.domain.model.login.User;
import com.seventeam.algoritmgameproject.web.dto.game_dto.EnterAndExitRoomRequestDto;
import com.seventeam.algoritmgameproject.domain.model.game.GameMessage;
import com.seventeam.algoritmgameproject.domain.model.game.GameRoom;
import com.seventeam.algoritmgameproject.domain.model.game.ReadyMessage;
import com.seventeam.algoritmgameproject.domain.model.game.UserGameInfo;

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

    void disconnectEvent(String username);

    UserGameInfo userToUserInfo(User user);
}
