package com.seventeam.algoritmgameproject.web.socketServer.model.Dto;

import com.seventeam.algoritmgameproject.web.socketServer.model.LoseMessage;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Getter
@NoArgsConstructor
public class LoseMessageDto implements Serializable {
    private static final long serialVersionUID = 5383678978059006611L;
    private String roomId;
    private LoseMessage loseMessage;
    private String to;

    public LoseMessageDto(String roomId, LoseMessage loseMessage, String to) {
        this.roomId = roomId;
        this.loseMessage = loseMessage;
        this.to = to;
    }
}
