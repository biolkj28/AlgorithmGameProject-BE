package com.seventeam.algoritmgameproject.web.socketServer.pubsub;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.seventeam.algoritmgameproject.web.socketServer.model.Dto.FailMessageAndToDto;
import com.seventeam.algoritmgameproject.web.socketServer.model.Dto.LoseMessageDto;
import com.seventeam.algoritmgameproject.web.socketServer.model.Dto.UserAndRoomIdDto;
import com.seventeam.algoritmgameproject.web.socketServer.model.GameMessage;
import com.seventeam.algoritmgameproject.web.socketServer.model.ReadyMessage;
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
    private static final String READY = "READY";
    private static final String GAME = "GAME";
    private static final String USERINFO = "USERINFO";
    private static final String OPFAIL = "OPFAIL";
    private static final String LOSE = "LOSE";

    /**
     * Redis에서 메시지가 발행(publish)되면 대기하고 있던 onMessage가 해당 메시지를 받아 처리한다.
     */

    public void sendMessage(String publishMessage) {
        try {
            // redis에서 발행된 데이터를 받아 deserialize

            log.info("ONMESSAGE:{}", publishMessage);

            if (publishMessage == null) {
                throw new RuntimeException("메시지 오류");
            }

            if (publishMessage.contains(READY)) {

                ReadyMessage roomMessage = objectMapper.readValue(publishMessage, ReadyMessage.class);
                send(roomMessage.getRoomId(), roomMessage);

            } else if (publishMessage.contains(GAME)) {

                GameMessage roomMessage = objectMapper.readValue(publishMessage, GameMessage.class);
                sendToUser(roomMessage);

            } else if (publishMessage.contains(USERINFO)) {

                UserAndRoomIdDto userGameInfoAndRoomId = objectMapper.readValue(publishMessage, UserAndRoomIdDto.class);
                sendUserInfo(userGameInfoAndRoomId);

            } else if (publishMessage.contains(OPFAIL)) {

                FailMessageAndToDto failMessageAndToDto = objectMapper.readValue(publishMessage, FailMessageAndToDto.class);
                sendOpFailMessage(failMessageAndToDto);

            } else if (publishMessage.contains(LOSE)) {

                LoseMessageDto loseMessageDto = objectMapper.readValue(publishMessage, LoseMessageDto.class);
                sendLoseMessage(loseMessageDto);

            }

        } catch (Exception e) {
            log.error(e.getMessage());
        }
    }

    //구독한 사람에게 send
    public void send(String roomId, Object roomMessage) {
        if(roomId!=null){
            messagingTemplate.convertAndSend("/topic/game/room/" + roomId, roomMessage);
        }
    }


    //특정사용자에게 send
    public void sendToUser(GameMessage gameMessage) {
        log.info("SEND:{}", gameMessage.getTo());
        if(gameMessage.getTo()!=null){
            messagingTemplateTo.convertAndSendToUser(gameMessage.getTo(), "/queue/game/codeMessage/" + gameMessage.getRoomId(), gameMessage);
        }
    }

    public void sendUserInfo(UserAndRoomIdDto dto) {
        messagingTemplate.convertAndSend("/topic/game/room/" + dto.getRoomId(), dto.getInfo());
    }

    public void sendOpFailMessage(FailMessageAndToDto dto) {
        log.info("SEND:{}", dto.getTo());
        if (dto.getTo() != null) {
            messagingTemplate.convertAndSendToUser(dto.getTo(), "/queue/game/codeMessage/" + dto.getRoomId(), dto.getMessage());
        }
    }

    public void sendLoseMessage(LoseMessageDto dto) {
        log.info("SEND:{}", dto.getTo());
        if (dto.getTo() != null) {
            messagingTemplate.convertAndSendToUser(dto.getTo(), "/queue/game/codeMessage/" + dto.getRoomId(), dto.getLoseMessage());
        }
    }
}
