package com.seventeam.algoritmgameproject.web.socketServer.model;

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
    public String title;
    public String question;
    public String template;

}
