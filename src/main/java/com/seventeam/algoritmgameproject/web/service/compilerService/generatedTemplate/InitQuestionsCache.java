package com.seventeam.algoritmgameproject.web.service.compilerService.generatedTemplate;

import com.seventeam.algoritmgameproject.domain.repository.QuestionDslRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class InitQuestionsCache {
    private final QuestionDslRepository repository;

    public void init(){
        repository.findAllQuestions();
    }
}
