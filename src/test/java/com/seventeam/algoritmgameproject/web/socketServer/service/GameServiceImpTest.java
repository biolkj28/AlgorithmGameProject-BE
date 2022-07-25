package com.seventeam.algoritmgameproject.web.socketServer.service;

import com.seventeam.algoritmgameproject.domain.model.login.User;
import com.seventeam.algoritmgameproject.web.repository.UserRepository;
import com.seventeam.algoritmgameproject.web.dto.game_dto.EnterAndExitRoomRequestDto;
import com.seventeam.algoritmgameproject.domain.model.game.GameRoom;
import com.seventeam.algoritmgameproject.web.repository.game_repository.GameSessionRepository;
import com.seventeam.algoritmgameproject.web.service.game_service.GameService;
import lombok.extern.slf4j.Slf4j;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@Slf4j
@SpringBootTest
class GameServiceImpTest {
    @Autowired
    GameService service;
    @Autowired
    UserRepository repository;

    @Autowired
    GameSessionRepository sessionRepository;

    @Test
    @DisplayName("방장이 나가고, 권한이 승격되고, 승격된 참여자 나갔을 때 결과")
    void exitRoom() {

        User user = repository.findByUserId("biolkj28").orElseThrow(()-> new NullPointerException("없는 사용자 입니다."));
        User user1 = repository.findByUserId("ljc4602").orElseThrow(()-> new NullPointerException("없는 사용자 입니다."));
        GameRoom room = service.createRoom(0, 0, user);
        EnterAndExitRoomRequestDto dto = new EnterAndExitRoomRequestDto(room.getRoomId(), room.getServer());
        service.enterRoom(dto,user1);
        List<String> sessions = sessionRepository.findSessions(room.getRoomId());
        for (String session : sessions) {
          log.info("참여자:{}",session);
        }
        service.exitRoom(room.getServer(),room.getRoomId(),user);
        log.info("남은 인원:{}", sessionRepository.findSessions(room.getRoomId()));

        service.exitRoom(room.getServer(),room.getRoomId(),user1);
        log.info("남은 인원:{}", sessionRepository.findSessions(room.getRoomId()));
    }

    @Test
    @DisplayName("참여자가 나가고, 방장이 나가기 및 탈주 확인")
    void exitRoom1() {

        User user = repository.findByUserId("biolkj28").orElseThrow(()-> new NullPointerException("없는 사용자 입니다."));
        User user1 = repository.findByUserId("ljc4602").orElseThrow(()-> new NullPointerException("없는 사용자 입니다."));
        GameRoom room = service.createRoom(0, 0, user);
        EnterAndExitRoomRequestDto dto = new EnterAndExitRoomRequestDto(room.getRoomId(), room.getServer());

        service.enterRoom(dto,user1);
        List<String> sessions = sessionRepository.findSessions(room.getRoomId());
        for (String session : sessions) {
            log.info("참여자:{}",session);
        }
        service.exitRoom(room.getServer(),room.getRoomId(),user1);
        log.info("남은 인원:{}", sessionRepository.findSessions(room.getRoomId()));
        log.info("탈주:{}",sessionRepository.notYetExit(user1.getUserId()));
        Assertions.assertThat(sessionRepository.notYetExit(user1.getUserId())).isFalse();

        if(sessionRepository.notYetExit(user1.getUserId())){
            service.disconnectEvent(user1.getUserId());
        }

        log.info("탈주:{}",sessionRepository.notYetExit(user.getUserId()));
        Assertions.assertThat(sessionRepository.notYetExit(user1.getUserId())).isFalse();
        log.info("남은 인원:{}", sessionRepository.findSessions(room.getRoomId()));
        if(sessionRepository.notYetExit(user.getUserId())){
            service.disconnectEvent(user.getUserId());
        }
    }
}