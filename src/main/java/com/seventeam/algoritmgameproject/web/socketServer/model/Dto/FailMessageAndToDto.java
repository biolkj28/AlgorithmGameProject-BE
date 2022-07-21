package com.seventeam.algoritmgameproject.web.socketServer.model.Dto;

import com.seventeam.algoritmgameproject.web.socketServer.model.OpFailMessage;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Getter
@NoArgsConstructor
public class FailMessageAndToDto implements Serializable {
    private static final long serialVersionUID = 6494678978059006611L;

    private final String type = "OPFAIL";
    private String roomId;
    private OpFailMessage message;
    private String To;


    public FailMessageAndToDto(String roomId, OpFailMessage message, String to) {
        this.roomId = roomId;
        this.message = message;
        To = to;
    }
}
