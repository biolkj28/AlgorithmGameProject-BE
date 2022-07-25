package com.seventeam.algoritmgameproject;


import com.seventeam.algoritmgameproject.web.service.crawling_service.QuestionCrawlingService;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class QuestionInit implements ApplicationRunner {
    private final QuestionCrawlingService service;

    @Override
    public void run(ApplicationArguments args) {
        //service.saveDefaultQuestions();
    }
}
