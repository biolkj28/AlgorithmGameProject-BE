package com.seventeam.algoritmgameproject.web.service.game_service;

import com.seventeam.algoritmgameproject.domain.model.game.GameProcessMessage;
import com.seventeam.algoritmgameproject.domain.model.login.User;
import com.seventeam.algoritmgameproject.web.repository.UserRepository;
import com.seventeam.algoritmgameproject.web.repository.game_repository.GameSessionRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.listener.ChannelTopic;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import java.util.Optional;

import static com.seventeam.algoritmgameproject.domain.model.game.MessageType.*;

@Slf4j
@Service
@RequiredArgsConstructor
public class GameProcessImp implements GameProcess {

    private final UserRepository userRepository;
    private final GameSessionRepository sessionRepository;
    private final ChannelTopic channelTopic;
    private final RedisTemplate<String, Object> redisTemplate;

    @Override
    public void GameProcessManager(GameProcessMessage.Request request) {
        if (request.getType().equals(EXIT_LOSE) || request.getType().equals(COMPILE_FAIL_LOSE)) {
            exitAndComPile3FailWinMessage(request);
        } else if (request.getType().equals(TIMEOUT)) {
            timeoutMessage(request);
        } else if (request.getType().equals(EXIT)) {
            exit(request);
        }
    }

    @Override
    public void exit(GameProcessMessage.Request request) {
        sendProcessMessage(request);
    }

    @Override
    @Transactional
    public void exitAndComPile3FailWinMessage(GameProcessMessage.Request request) {
        User user = getUnWrapUser(request.getUsername());
        String othersSession = Optional.of(sessionRepository.findOthersSession(request.getRoomId(), request.getUsername()))
                .orElseThrow(() -> new NullPointerException("상대방이 나갔습니다."));

        //패배 처리
        lose(user);

        // 상대 승리 처리
        User others = getUnWrapUser(othersSession);
        others.win();
        userRepository.save(others);

        GameProcessMessage.Request winResponse = new GameProcessMessage.Request(
                WIN,
                request.getUsername(),
                request.getRoomId());
        winResponse.setTo(othersSession);

        log.info("전송:{}", winResponse.getTo());
        redisTemplate.convertAndSend(channelTopic.getTopic(), winResponse);
    }

    @Override
    @Transactional
    public void timeoutMessage(GameProcessMessage.Request request) {
        User user = getUnWrapUser(request.getUsername());
        lose(user);
    }

    @Override
    public void compileFail(String username, String roomId) {
        sendProcessMessage(new GameProcessMessage.Request(FAIL, username, roomId));
    }

    public void loseMessage(String username, String roomId) {
        sendProcessMessage(new GameProcessMessage.Request(LOSE, username, roomId));
    }

    @Override
    public void sendProcessMessage(GameProcessMessage.Request request) {
        String otherUserId = Optional.of(sessionRepository.findOthersSession(request.getRoomId(), request.getUsername()))
                .orElseThrow(() -> new NullPointerException("상대방이 나갔습니다."));

        request.setTo(otherUserId);
        redisTemplate.convertAndSend(channelTopic.getTopic(), request);

    }

    @Transactional
    public void winAndOtherLoseProcess(String roomId, User user) {
        winProcess(user);
        loseMessage(user.getUserId(), roomId);
        loseProcess(roomId, user);
    }

    @Override
    @Transactional
    public void lose(User user) {
        //본인 패배 처리
        user.lose();
        userRepository.save(user);
    }

    public void winProcess(User user) {
        User winUser = userRepository.findByUserId(user.getUserId()).orElseThrow(() -> new NullPointerException("없는 유저 입니다."));
        winUser.win();
        userRepository.save(winUser);
    }

    public void loseProcess(String roomId, User user) {
        String othersSession = sessionRepository.findOthersSession(roomId, user.getUserId());
        User loseUser = userRepository.findByUserId(othersSession).orElseThrow(() -> new NullPointerException("없는 유저 입니다."));
        loseUser.lose();
        userRepository.save(loseUser);
    }


    @Override
    @Transactional(readOnly = true)
    public User getUnWrapUser(String username) {
        return userRepository.findByUserId(username)
                .orElseThrow(() -> new NullPointerException("없는 사용자 입니다."));
    }


}
