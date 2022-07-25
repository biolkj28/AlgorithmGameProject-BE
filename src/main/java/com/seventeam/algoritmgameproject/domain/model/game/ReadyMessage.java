package com.seventeam.algoritmgameproject.domain.model.game;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class ReadyMessage {
    private final String type = "READY";
    private String server;
    private String roomId;
    private String sender;
    public String title;
    public String question;
    public String template;

}
