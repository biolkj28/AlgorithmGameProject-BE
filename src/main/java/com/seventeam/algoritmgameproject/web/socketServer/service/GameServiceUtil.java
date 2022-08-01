package com.seventeam.algoritmgameproject.web.socketServer.service;

import com.seventeam.algoritmgameproject.domain.QuestionLevel;
import com.seventeam.algoritmgameproject.web.service.compilerService.Language;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class GameServiceUtil {
    public String findServerName(int langIdx, int levelIdx) {
        StringBuilder server = new StringBuilder(10);

        switch (langIdx) {
            case 0: {
                server.append(Language.JAVA.name());
                break;
            }
            case 1: {
                server.append(Language.NODEJS.name());
                break;
            }
            case 2: {
                server.append(Language.PYTHON3.name());
                break;
            }
        }
        switch (levelIdx) {
            case 0: {
                server.append(QuestionLevel.EASY.name());
                break;
            }
            case 1: {
                server.append(QuestionLevel.NORMAL.name());
                break;
            }
            case 2: {
                server.append(QuestionLevel.HARD.name());
                break;
            }
        }
        return server.toString();
    }

    public Language getSelectedLang(int langIdx){

        switch (langIdx) {
            case 0: {
               return Language.JAVA;
            }
            case 1: {
                return Language.NODEJS;

            }
            case 2: {
                return Language.PYTHON3;
            }
            default:{
                throw new IllegalArgumentException("지원하지 않는 언어 입니다.");
            }
        }
    }

    public QuestionLevel getSelectedLevel(int questionLevelIdx){
        switch (questionLevelIdx){
            case 0: {
                return QuestionLevel.EASY;
            }
            case 1: {
                return QuestionLevel.NORMAL;

            }
            case 2: {
                return QuestionLevel.HARD;
            }
            default:{
                throw new IllegalArgumentException("지원하지 않는 언어 입니다.");
            }
        }
    }

    /**
     * destination정보에서 roomId 추출
     */
    public String getRoomId(String destination) {
        int lastIndex = destination.lastIndexOf('/');
        if (lastIndex != -1)
            return destination.substring(lastIndex + 1);
        else
            return "";
    }
}
