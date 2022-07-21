package com.seventeam.algoritmgameproject.config.stomp;

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

    private final GameService service;


    @Override
    public Message<?> preSend(Message<?> message, MessageChannel channel) {
        StompHeaderAccessor accessor = StompHeaderAccessor.wrap(message);
        String destination = Optional.ofNullable(accessor.getDestination()).orElse("notFoundRoomId");
        String roomId = getRoomId(destination);
        String username = Objects.requireNonNull(message.getHeaders().get("simpUser")).toString();

        if (StompCommand.SUBSCRIBE == accessor.getCommand()) {
            //유저 정보 전송 , 구독 주소 변경
            if (service.isParticipant(roomId,username)) {
                log.info("Connect User: {}", username);
                service.sendToMyUserInfo(roomId, username);
            }

        } else if (StompCommand.DISCONNECT == accessor.getCommand()) {
            log.info("Disconnect username: {}", username);
            service.disconnectEvent(roomId,username);
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
