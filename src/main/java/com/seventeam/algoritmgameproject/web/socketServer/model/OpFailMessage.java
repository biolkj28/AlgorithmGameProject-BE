package com.seventeam.algoritmgameproject.web.socketServer.model;

import lombok.Getter;

@Getter
public class OpFailMessage {

    private final String type = "OPFAIL";
    private final String msg = "상대방 오답 제출";

}
