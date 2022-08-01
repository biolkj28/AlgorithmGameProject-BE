package com.seventeam.algoritmgameproject.web.service.game_service;

import com.seventeam.algoritmgameproject.domain.QuestionLevel;
import com.seventeam.algoritmgameproject.domain.model.game.*;
import com.seventeam.algoritmgameproject.domain.model.login.User;
import com.seventeam.algoritmgameproject.web.dto.game_dto.EnterAndExitRoomRequestDto;
import com.seventeam.algoritmgameproject.web.dto.game_dto.FindRoomsResult;
import com.seventeam.algoritmgameproject.web.dto.questions_dto.QuestionRedis;

public interface GameService {
    //방 조회
    FindRoomsResult findRooms(int langIdx, int levelIdx, String username);

    // 방생성과 동시에 해당 난이도, 언어에 맞는 랜덤 문제 할당
    GameRoom createRoom(int langIdx, int questionLevelIdx, User user);

    //입장 가능 확인
    boolean isEnter(String server, String roomId);

    //방 입장, 본인 정보 전송, userDetailPrincipal 추가
    UserGameInfo enterRoom(EnterAndExitRoomRequestDto dto, User user);

    //Redis 입장 처리
    void saveEnterInfoRedis(GameRoom room, User user);

    // 방 퇴장 메서드
    void exitRoom(String server, String roomId, User user);

    // 연결 해제 시 해당 방 관련 데이터 삭제 메서드
    void disconnectEvent(String username);

    // 퇴장 처리 메서드
    void exitEvent(GameRoom room, String username, String role);
    // 방 전적 정보 갱신
    void updateGameRoom(String roomId);

    //준비 메시지 처리
    void ready(ReadyMessage message);
    // 시작 메시지 수신 시 문제 전송
    void sendQuestion(GameProcessMessage.Request request);
    // 참여자 확인
    Boolean isParticipant(String roomId, String username);
    // 실시간 코드 송.수신 메시지
    void sendGameCode(GameMessage message);
    // 사용자 정보 메시지 전송
    void sendToMyUserInfo(String roomId, String username);
    //랜덤 문제 전송
    QuestionRedis randomQuestions(QuestionLevel level);
    //사용자 정보 전송
    public UserGameInfo userToUserInfo(User user);
}
