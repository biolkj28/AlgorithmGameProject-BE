package com.seventeam.algoritmgameproject.web.service.compiler_service.generatedTemplate;

import com.seventeam.algoritmgameproject.web.repository.questions_repository.QuestionDslRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class InitQuestionsCache {
    private final QuestionDslRepository repository;

    public void init(){
        repository.findAllQuestions();
    }
}
