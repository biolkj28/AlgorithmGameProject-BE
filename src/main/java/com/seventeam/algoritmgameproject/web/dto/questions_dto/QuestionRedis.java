package com.seventeam.algoritmgameproject.web.dto.questions_dto;


import com.seventeam.algoritmgameproject.domain.QuestionLevel;
import com.seventeam.algoritmgameproject.domain.model.questions.TestCase;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.*;

@Getter@Setter @ToString
public class QuestionRedis {
    private Long id;
    private String title;
    private String question;
    private String limitation;
    private String inOutExHead;
    private String inOutEx;
    private String inOutExDescription;
    private String reference;
    private QuestionLevel level;
    private Map<String, String> templates = new HashMap<>();
    private Set<TestCase> cases = new LinkedHashSet<>();

}
