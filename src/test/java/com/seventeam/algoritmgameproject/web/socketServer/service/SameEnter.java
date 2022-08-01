//package com.seventeam.algoritmgameproject.web.socketServer.service;
//
//import com.fasterxml.jackson.core.JsonProcessingException;
//import com.seventeam.algoritmgameproject.domain.model.game.ReadyMessage;
//import com.seventeam.algoritmgameproject.domain.model.login.User;
//import com.seventeam.algoritmgameproject.security.JwtTokenProvider;
//import com.seventeam.algoritmgameproject.web.dto.game_dto.FindRoomsResult;
//import com.seventeam.algoritmgameproject.web.repository.UserRedisRepository;
//import com.seventeam.algoritmgameproject.web.repository.UserRepository;
//import com.seventeam.algoritmgameproject.web.dto.game_dto.EnterAndExitRoomRequestDto;
//import com.seventeam.algoritmgameproject.domain.model.game.GameRoom;
//import com.seventeam.algoritmgameproject.web.repository.game_repository.GameRoomRepository;
//import com.seventeam.algoritmgameproject.web.repository.game_repository.GameSessionRepository;
//import com.seventeam.algoritmgameproject.web.service.game_service.GameProcess;
//import com.seventeam.algoritmgameproject.web.service.game_service.GameService;
//import lombok.extern.slf4j.Slf4j;
//import org.junit.jupiter.api.DisplayName;
//import org.junit.jupiter.api.Test;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.data.redis.core.ListOperations;
//import org.springframework.data.redis.core.RedisTemplate;
//
//import java.util.ArrayList;
//import java.util.List;
//import java.util.concurrent.CountDownLatch;
//import java.util.concurrent.ExecutorService;
//import java.util.concurrent.Executors;
//
//@Slf4j
//@SpringBootTest
//class GameServiceImpTest {
//    @Autowired
//    GameService service;
//    @Autowired
//    JwtTokenProvider jwtTokenProvider;
//    @Autowired
//    GameRoomRepository gameRoomRepository;
//    @Autowired
//    UserRepository repository;
//
//    @Autowired
//    GameSessionRepository sessionRepository;
//
//    @Autowired
//    UserRedisRepository userRedisRepository;
//
//    @Autowired
//    RedisTemplate<String, Object> redisTemplate;
//
//    @Autowired
//    GameProcess gameProcess;
//
//    private static final ExecutorService executorService =
//            Executors.newFixedThreadPool(3);
//
//    @Test
//    @DisplayName("방장이 나가고, 권한이 승격되고, 승격된 참여자 나갔을 때 결과")
//    void exitRoom() {
//
//        User user = repository.findByUserId("biolkj28").orElseThrow(() -> new NullPointerException("없는 사용자 입니다."));
//        User user1 = repository.findByUserId("ljc4602").orElseThrow(() -> new NullPointerException("없는 사용자 입니다."));
//        GameRoom room = service.createRoom(0, 0, user);
//        EnterAndExitRoomRequestDto dto = new EnterAndExitRoomRequestDto(room.getRoomId(), room.getServer());
//        service.enterRoom(dto, user1);
//        List<String> sessions = sessionRepository.findSessions(room.getRoomId());
//        for (String session : sessions) {
//            log.info("참여자:{}", session);
//        }
//        service.exitRoom(room.getServer(), room.getRoomId(), user);
//        log.info("남은 인원:{}", sessionRepository.findSessions(room.getRoomId()));
//
//        service.exitRoom(room.getServer(), room.getRoomId(), user1);
//        log.info("남은 인원:{}", sessionRepository.findSessions(room.getRoomId()));
//    }
//
//    @Test
//    @DisplayName("참여자가 나가고, 방장이 나가기 및 탈주 확인")
//    void exitRoom1() {
//
//        User user = repository.findByUserId("biolkj28").orElseThrow(() -> new NullPointerException("없는 사용자 입니다."));
//        User user1 = repository.findByUserId("ljc4602").orElseThrow(() -> new NullPointerException("없는 사용자 입니다."));
//
//        userRedisRepository.saveOrUpdateUserGameInfoCache(service.userToUserInfo(user));
//        userRedisRepository.saveOrUpdateUserGameInfoCache(service.userToUserInfo(user1));
//
//        GameRoom room = service.createRoom(0, 0, user);
//        EnterAndExitRoomRequestDto dto = new EnterAndExitRoomRequestDto(room.getRoomId(), room.getServer());
//
//        log.info("입장 전:{}", room.getCreatorGameInfo().toString());
//        service.enterRoom(dto, user1);
//        gameProcess.lose(user);
//        service.exitRoom(room.getServer(), room.getRoomId(), user1);
//        GameRoom roomById = gameRoomRepository.findRoomById(room.getServer(), room.getRoomId());
//        log.info("퇴장 후 갱신:{}", roomById.getCreatorGameInfo().toString());
//        FindRoomsResult rooms = service.findRooms(0, 0, user1.getUserId());
//        for (GameRoom gameRoom : rooms.getGameRooms()) {
//            log.info("방 조회:{}", gameRoom.getCreatorGameInfo().toString());
//        }
////        log.info("남은 인원:{}", sessionRepository.findSessions(room.getRoomId()));
////        log.info("탈주:{}",sessionRepository.notYetExit(user1.getUserId()));
////        Assertions.assertThat(sessionRepository.notYetExit(user1.getUserId())).isFalse();
////
////        if(sessionRepository.notYetExit(user1.getUserId())){
////            service.disconnectEvent(user1.getUserId());
////        }
////
////        log.info("탈주:{}",sessionRepository.notYetExit(user.getUserId()));
////        Assertions.assertThat(sessionRepository.notYetExit(user1.getUserId())).isFalse();
////        log.info("남은 인원:{}", sessionRepository.findSessions(room.getRoomId()));
////        if(sessionRepository.notYetExit(user.getUserId())){
////            service.disconnectEvent(user.getUserId());
////        }
//    }
//
//    @Test
//    @DisplayName("레디 확인")
//    void ready() throws InterruptedException {
//        User user = repository.findByUserId("biolkj28").orElseThrow(() -> new NullPointerException("없는 사용자 입니다."));
//        User user1 = repository.findByUserId("ljc4602").orElseThrow(() -> new NullPointerException("없는 사용자 입니다."));
//        GameRoom room = service.createRoom(0, 0, user);
//        ReadyMessage message = new ReadyMessage();
//        message.setRoomId(room.getRoomId());
//        message.setServer(room.getServer());
//        List<User> list = new ArrayList<>();
//        list.add(user);
//        list.add(user1);
//
//        int numberOfThreads = 2;
//        ExecutorService sservice = Executors.newFixedThreadPool(10);
//        CountDownLatch latch = new CountDownLatch(numberOfThreads);
//        ReadyMessage message1 = new ReadyMessage();
//        message1.setServer(room.getServer());
//        message1.setRoomId(room.getRoomId());
//        for (int i = 0; i < numberOfThreads; i++) {
//            int finalI = i;
//            sservice.submit(() -> {
//                log.info("쓰레드 번호:{}",finalI);
//                service.ready(message1);
//                latch.countDown();
//            });
//        }
//        latch.await();
//        // EnterAndExitRoomRequestDto dto = new EnterAndExitRoomRequestDto(room.getRoomId(), room.getServer());
//
//
//    }
//
//    @Test
//    @DisplayName("jwt 발급")
//    public void jwt() throws JsonProcessingException {
//        ListOperations<String, Object> stringObjectListOperations = redisTemplate.opsForList();
//        List<String> list = new ArrayList<>();
//        for (int i = 0; i < 10; i++) {
//            list.add(String.valueOf(i));
//
//        }
//        stringObjectListOperations.rightPushAll("test1", list);
//
//        Long size = stringObjectListOperations.size("test1");
//        List<Object> test1 = stringObjectListOperations.range("test1", 0, size - 1);
//        test1.forEach((data) -> log.info("출력:{}", data));
//
//    }
//
//    @Test
//    @DisplayName("입장 ")
//    public void enter() throws InterruptedException {
//
//        User user = repository.findByUserId("test3").orElseThrow(() -> new NullPointerException("없는 사용자 입니다."));
//        User user1 = repository.findByUserId("test0").orElseThrow(() -> new NullPointerException("없는 사용자 입니다."));
//        User user2 = repository.findByUserId("test2").orElseThrow(() -> new NullPointerException("없는 사용자 입니다."));
//        GameRoom room = service.createRoom(0, 0, user2);
//        log.info("방 생성:{}",room.isEnter());
//        EnterAndExitRoomRequestDto dto = new EnterAndExitRoomRequestDto(room.getRoomId(), room.getServer());
//
//        List<User> list = new ArrayList<>();
//        list.add(user);
//        list.add(user1);
//
//        int numberOfThreads = 2;
//        ExecutorService sservice = Executors.newFixedThreadPool(10);
//        CountDownLatch latch = new CountDownLatch(numberOfThreads);
//
//        for (int i = 0; i < numberOfThreads; i++) {
//            int finalI = i;
//            sservice.submit(() -> {
//                log.info("쓰레드 번호:{}",finalI);
//                service.enterRoom(dto, list.get(finalI));
//                log.info("입장 처리");
//                latch.countDown();
//            });
//        }
//        latch.await();
//        GameRoom roomById = gameRoomRepository.findRoomById(room.getServer(), room.getRoomId());
//        log.info("방 처리 여부:{}",roomById.isEnter());
//        String othersSession = sessionRepository.findOthersSession(roomById.getRoomId(), user2.getUserId());
//        String roomIdBySession = sessionRepository.findRoomIdBySession(othersSession);
//        log.info("입장한 사람:{}, 참여한 방:{}", othersSession,roomIdBySession);
//
//
//    }
//
//}
//
//
