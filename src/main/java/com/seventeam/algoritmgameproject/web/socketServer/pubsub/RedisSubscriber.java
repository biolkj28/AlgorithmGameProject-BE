package com.seventeam.algoritmgameproject.web.socketServer.pubsub;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.seventeam.algoritmgameproject.web.socketServer.model.GameMessage;
import com.seventeam.algoritmgameproject.web.socketServer.model.ReadyMessage;
import com.seventeam.algoritmgameproject.web.socketServer.repository.GameRoomRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Component;


@Slf4j
@RequiredArgsConstructor
@Component
public class RedisSubscriber {
    private final ObjectMapper objectMapper;
    private final SimpMessageSendingOperations messagingTemplate;
    private final SimpMessagingTemplate messagingTemplateTo;


    /**
     * Redis에서 메시지가 발행(publish)되면 대기하고 있던 onMessage가 해당 메시지를 받아 처리한다.
     */

    public void sendMessage(String publishMessage) {
        try {
            // redis에서 발행된 데이터를 받아 deserialize

            log.info("ONMESSAGE:{}", publishMessage);

            if (publishMessage != null && publishMessage.contains("READY")) {
                ReadyMessage roomMessage = objectMapper.readValue(publishMessage, ReadyMessage.class);
                send(roomMessage.getRoomId(), roomMessage);
            } else {
                GameMessage roomMessage = objectMapper.readValue(publishMessage, GameMessage.class);
                log.info("데이터:{}",roomMessage.toString());
                sendToUser(roomMessage);
                log.info("전송");
            }

        } catch (Exception e) {
            log.error(e.getMessage());
        }
    }

    //구독한 사람에게 send
    public void send(String roomId, Object roomMessage) {
        messagingTemplate.convertAndSend("/topic/game/room/" + roomId, roomMessage);
    }

    //특정사용자에게 send
    public void sendToUser(GameMessage gameMessage) {
        log.info("SEND:{}",gameMessage.getTo());
        messagingTemplateTo.convertAndSendToUser(gameMessage.getTo(), "/queue/game/codeMessage", gameMessage);
    }
}
