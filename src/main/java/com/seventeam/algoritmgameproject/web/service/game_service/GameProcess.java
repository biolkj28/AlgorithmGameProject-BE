package com.seventeam.algoritmgameproject.web.service.game_service;

import com.seventeam.algoritmgameproject.domain.model.game.GameProcessMessage;
import com.seventeam.algoritmgameproject.domain.model.login.User;
import org.springframework.transaction.annotation.Transactional;

public interface GameProcess {
    void GameProcessManager(GameProcessMessage.Request request);

    @Transactional
    void exitAndComPile3FailWinMessage(GameProcessMessage.Request request);

    @Transactional
    void timeoutMessage(GameProcessMessage.Request request);

    void compileFail(String username, String roomId);

    void loseMessage(String username, String roomId);
    void sendProcessMessage(GameProcessMessage.Request request);

    void lose(User user);

    User getUnWrapUser(String username);
}
