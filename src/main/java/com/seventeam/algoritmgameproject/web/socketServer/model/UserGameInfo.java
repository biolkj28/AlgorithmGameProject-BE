package com.seventeam.algoritmgameproject.web.socketServer.model;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Getter
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class UserGameInfo implements Serializable {
    private static final long serialVersionUID = 6494678977089006628L;

    private final String type = "USERINFO";
    private String playerName;
    private String profileUrl;
    private int winCnt;
    private int loseCnt;


    @Builder
    private UserGameInfo(String playerName, String profileUrl, int winCnt, int loseCnt) {
        this.playerName = playerName;
        this.profileUrl = profileUrl;
        this.winCnt = winCnt;
        this.loseCnt = loseCnt;
    }
}
