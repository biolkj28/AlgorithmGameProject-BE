package com.seventeam.algoritmgameproject.web.dto;


import lombok.AccessLevel;
import lombok.Builder;
import lombok.NoArgsConstructor;
import lombok.ToString;


@NoArgsConstructor(access = AccessLevel.PRIVATE)
@ToString
public class CompileResultDto {
    private String msg;
    private boolean result;

    @Builder
    public CompileResultDto(String msg, boolean result) {
        this.msg = msg;
        this.result = result;
    }

}
