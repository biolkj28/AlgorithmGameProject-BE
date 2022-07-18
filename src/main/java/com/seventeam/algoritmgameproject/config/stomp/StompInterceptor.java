package com.seventeam.algoritmgameproject.config.stomp;

import com.seventeam.algoritmgameproject.web.socketServer.repository.GameRoomRepository;
import com.seventeam.algoritmgameproject.web.socketServer.service.GameService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.simp.stomp.StompCommand;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.messaging.support.ChannelInterceptor;
import org.springframework.stereotype.Component;

import java.util.Objects;
import java.util.Optional;

@Slf4j
@Component
@RequiredArgsConstructor
public class StompInterceptor implements ChannelInterceptor {
    private final GameRoomRepository repository;
    @Override
    public Message<?> preSend(Message<?> message, MessageChannel channel) {
        StompHeaderAccessor accessor = StompHeaderAccessor.wrap(message);
        String sessionId = accessor.getSessionId();
        String destination = Optional.ofNullable(accessor.getDestination()).orElse("notFoundRoomId");
        String roomId = getRoomId(destination);

        if (StompCommand.SUBSCRIBE == accessor.getCommand()) {
            if (destination.contains("/topic/game/room/")) {

                String connectionCode = Objects.requireNonNull(message.getHeaders().get("simpUser")).toString();
                log.info("Connect SessionId: {}", sessionId);
                log.info("ConnectionCode:{}", connectionCode);
                //세션 저장 및 연결 코드 발급, 추후 로그인 시 jwt 확인 파싱, sessionId -> userid 로 키값저장
                repository.saveEnterSession(roomId,sessionId,connectionCode);
            }

        } else if (StompCommand.DISCONNECT == accessor.getCommand()) {
            log.info("Disconnect SessionId: {}", sessionId);
            repository.deleteEnterSession(sessionId);
        }
        return message;
    }

    public String getRoomId(String destination) {
        int lastIndex = destination.lastIndexOf('/');
        if (lastIndex != -1)
            return destination.substring(lastIndex + 1);
        else
            return "";
    }
}
