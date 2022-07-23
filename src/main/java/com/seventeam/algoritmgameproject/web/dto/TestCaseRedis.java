package com.seventeam.algoritmgameproject.web.dto;


import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter@Setter@ToString
public class TestCaseRedis {

    private Long id;
    private String answer;
    private String params;
    private String type;
    private String ansType;

}
