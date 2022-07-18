package com.seventeam.algoritmgameproject.web.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Getter
@NoArgsConstructor
public class UserGameInfo implements Serializable {
    private static final long serialVersionUID = 6494678977089006628L;
    private String playerName;
    private String profileUrl;
    private Long winCnt;
    private Long loseCnt;

    public UserGameInfo(String playerName, String profileUrl, Long winCnt, Long loseCnt) {
        this.playerName = playerName;
        this.profileUrl = profileUrl;
        this.winCnt = winCnt;
        this.loseCnt = loseCnt;
    }
}
