package com.seventeam.algoritmgameproject.web.service.game_service;

import com.seventeam.algoritmgameproject.domain.model.game.GameProcessMessage;
import com.seventeam.algoritmgameproject.domain.model.login.User;

public interface GameProcess {
    // 게임 중 수신 메시지 처리 매니저, 승리, 패배, 컴파일 3번 실패, 타임아웃 ,탈주
    void GameProcessManager(GameProcessMessage.Request request);

    // 상대에게 퇴장 메시지 전송
    void exit(GameProcessMessage.Request request);

    void exitAndComPile3FailWinMessage(GameProcessMessage.Request request);

    void timeoutMessage(GameProcessMessage.Request request);

    void compileFail(String username, String roomId);

    void loseMessage(String username, String roomId);
    void sendProcessMessage(GameProcessMessage.Request request);

    void winAndOtherLoseProcess(String roomId, User user);

    void winProcess(User user);
    void lose(User user);

    void loseProcess(String roomId, User user);
    User getUnWrapUser(String username);
}
