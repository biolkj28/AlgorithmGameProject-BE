package com.seventeam.algoritmgameproject.web.service.game_service;

import com.seventeam.algoritmgameproject.domain.model.game.GameProcessMessage;
import com.seventeam.algoritmgameproject.domain.model.login.User;
import com.seventeam.algoritmgameproject.web.repository.UserRepository;
import com.seventeam.algoritmgameproject.web.repository.game_repository.GameSessionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.listener.ChannelTopic;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import static com.seventeam.algoritmgameproject.domain.model.game.MessageType.*;

@Service
@RequiredArgsConstructor
public class GameProcessImp implements GameProcess {

    private final UserRepository userRepository;
    private final GameSessionRepository sessionRepository;
    private final ChannelTopic channelTopic;
    private final RedisTemplate<String, Object> redisTemplate;

    @Override
    public void GameProcessManager(GameProcessMessage.Request request) {
        if (request.getType().equals(EXIT_WIN) || request.getType().equals(COMPILE_FAIL_LOSE)) {
            exitAndComPile3FailWinMessage(request);
        } else if (request.getType().equals(TIMEOUT)) {
            timeoutMessage(request);
        }
    }

    @Override
    @Transactional
    public void exitAndComPile3FailWinMessage(GameProcessMessage.Request request) {
        User user = getUnWrapUser(request.getUsername());
        String othersSession = sessionRepository.findOthersSession(request.getRoomId(), request.getUsername());

        //패배 처리
        lose(user);

        if (othersSession != null) {
            // 상대 승리 처리
            User others = getUnWrapUser(othersSession);
            others.win();
            userRepository.save(others);
            request.setTo(othersSession);

        }
        GameProcessMessage.Request winResponse = new GameProcessMessage.Request(
                WIN,
                request.getUsername(),
                request.getRoomId());

        sendProcessMessage(winResponse);
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
        String otherUserId = sessionRepository.findOthersSession(request.getRoomId(), request.getTo());
        request.setTo(otherUserId);
        if (otherUserId != null) {
            request.setTo(otherUserId);
            redisTemplate.convertAndSend(channelTopic.getTopic(), request);
        }
    }

    @Override
    public void lose(User user) {
        if (user.getLoseCnt() > 0) {
            //본인 패배 처리
            user.lose();
            userRepository.save(user);
        }
    }


    @Override
    public User getUnWrapUser(String username) {
        return userRepository.findByUserId(username)
                .orElseThrow(() -> new NullPointerException("없는 사용자 입니다."));
    }


}
