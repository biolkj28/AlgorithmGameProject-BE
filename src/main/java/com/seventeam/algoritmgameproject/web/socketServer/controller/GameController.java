package com.seventeam.algoritmgameproject.web.socketServer.controller;


import com.seventeam.algoritmgameproject.web.socketServer.model.GameMessage;
import com.seventeam.algoritmgameproject.web.socketServer.model.ReadyMessage;
import com.seventeam.algoritmgameproject.web.socketServer.service.GameService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.stereotype.Controller;

@Slf4j
@Controller
@RequiredArgsConstructor
public class GameController {

    private final GameService service;

    //특정 유저로 수정
    @MessageMapping("/game/codeMessage")
    public void getCodeMessage(GameMessage message) {
      service.sendGameCode(message);
    }

    @MessageMapping("/game/ready")
    public void ready(ReadyMessage message) {
        log.info("READY:{}", message.toString());
        service.ready(message);
    }
}
