package com.seventeam.algoritmgameproject.web.service;


import com.seventeam.algoritmgameproject.domain.QuestionLevel;
import com.seventeam.algoritmgameproject.domain.model.Question;
import com.seventeam.algoritmgameproject.domain.repository.QuestionRepository;
import lombok.RequiredArgsConstructor;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
@RequiredArgsConstructor
public class QuestionCrawlingServiceImp implements QuestionCrawlingService {

    private final QuestionRepository repository;
    private static final String WEBDRIVER = "webdriver.chrome.driver";
    private static final String WEB_DRIVER_PATH = "C:/Users/biolk/Downloads/chromedriver.exe";
    private WebDriver driver;
    private ChromeOptions options;
    private static final String[] languages = {"java", "javascript", "python3"};
    private static final String[] QUESTION_URL = {
            //하
            "https://programmers.co.kr/learn/courses/30/lessons/12903",
            "https://programmers.co.kr/learn/courses/30/lessons/12925",
            "https://programmers.co.kr/learn/courses/30/lessons/12948",
            "https://programmers.co.kr/learn/courses/30/lessons/12954",

            //중
            "https://programmers.co.kr/learn/courses/30/lessons/82612",
            "https://programmers.co.kr/learn/courses/30/lessons/12901",
            "https://programmers.co.kr/learn/courses/30/lessons/12916",

            //상
            "https://programmers.co.kr/learn/courses/30/lessons/68935",
            "https://programmers.co.kr/learn/courses/30/lessons/12906",
            "https://programmers.co.kr/learn/courses/30/lessons/77484"

    };

    @PostConstruct
    void init() {
        System.setProperty(WEBDRIVER, WEB_DRIVER_PATH);
        options = new ChromeOptions();
        options.setHeadless(true);
        options.addArguments("--lang=ko");
        options.addArguments("--no-sandbox");
        options.addArguments("--disable-dev-shm-usage");
        options.addArguments("--disable-popup-blocking");
        options.addArguments("--disable-gpu");
        options.setCapability("ignoreProtectedModeSettings", true);

        //페이지가 로드될 때까지 대기
        //Normal: 로드 이벤트 실행이 반환 될 때 까지 기다린다.
        options.setPageLoadStrategy(PageLoadStrategy.NORMAL);
    }

    //기본 문제 저장
    @Transactional
    public void saveDefaultQuestions() {

        List<Question> list = new ArrayList<>();
        QuestionLevel level;
        for (int i = 0; i < QUESTION_URL.length; i++) {
            if (i < 4) {
                level = QuestionLevel.EASY;
            } else if (6 < i) {
                level = QuestionLevel.HARD;
            } else {
                level = QuestionLevel.NORMAL;
            }
            Question question = uploadQuestion(QUESTION_URL[i], level);
            list.add(question);
        }
        repository.saveAll(list);
    }

    //문제 추가
    public Question uploadQuestion(String questionUrl, QuestionLevel level) {

        boolean isGetQuestions = true;
        //문제 제목
        String title = null;
        //문제 설명
        String questionDescription = null;
        //제한 조건
        String limitation = null;
        //입출력 예
        String inOutExHead = null;
        String inOutEx = null;
        //입출력 예 설명
        String inOutExDescription = null;
        //참고 사항
        String reference = null;
        //난이도
        Map<String, String> codesTemplate = new HashMap<>();

        for (String language : languages) {
            try {
                //세션 시작
                driver = new ChromeDriver(options);
                driver.get(questionUrl + "?language=" + language);

                //한번만 문제 가져오기
                if (isGetQuestions) {
                    isGetQuestions = false;

                    title = driver.findElement(By.xpath("//*[@id=\"tab\"]/li")).getText();

                    questionDescription = driver.findElement(By.xpath("//*[@id=\"tour2\"]/div/div/p[1]")).getText();

                    limitation = driver.findElement(By.xpath("//*[@id=\"tour2\"]/div/div/ul")).getText();


                    try {
                        inOutExHead = driver.findElement(By.xpath("//*[@id=\"tour2\"]/div/div/table/thead")).getText();
                        inOutEx = driver.findElement(By.xpath("//*[@id=\"tour2\"]/div/div/table/tbody")).getText();
                    } catch (NoSuchElementException e) {
                        inOutExHead = "";
                        inOutEx = driver.findElement(By.xpath("//*[@id=\"tour2\"]/div/div/p[2]")).getText();
                    }


                    try {
                        inOutExDescription = driver.findElement(By.xpath("//*[@id=\"tour2\"]/div/div/p[2]")).getText();
                    } catch (NoSuchElementException e) {
                        inOutExDescription = "No Info";
                    }

                    try {
                        reference = driver.findElement(By.xpath("//*[@id=\"tour2\"]/div/div/ul[2]")).getText();
                    } catch (NoSuchElementException e) {
                        reference = "No Info";
                    }

                }

                //코드 태그 목록 추출, 합치기
                StringBuffer buffer = new StringBuffer();
                List<WebElement> solutions = driver.findElements(By.className("CodeMirror-line")); //모든 코드 줄 가져오기

                for (WebElement solution : solutions) {
                    String eachLine = solution.findElement(By.tagName("span")).getText();
                    buffer.append(eachLine);

                }
                codesTemplate.put(language, buffer.toString());
            } finally {
                driver.quit();
            }
        }
        return Question.builder()
                .title(title)
                .question(questionDescription)
                .limitation(limitation)
                .inOutExHead(inOutExHead)
                .inOutEx(inOutEx)
                .inOutExDescription(inOutExDescription)
                .reference(reference)
                .level(level)
                .templates(codesTemplate)
                .build();
    }

}
