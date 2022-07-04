package com.seventeam.algoritmgameproject;

import com.seventeam.algoritmgameproject.domain.QuestionLevel;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.DisplayName;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ProgrammersTest {

    private static final String WEBDRIVER = "webdriver.chrome.driver";
    private static final String WEB_DRIVER_PATH = "C:/Users/biolk/Downloads/chromedriver.exe";
    private WebDriver driver;
    private ChromeOptions options;
    private static final String[] QuestionUrl = {
            //하
            "https://programmers.co.kr/learn/courses/30/lessons/12903",
            "https://programmers.co.kr/learn/courses/30/lessons/12925",
            "https://programmers.co.kr/learn/courses/30/lessons/12948",
            "https://programmers.co.kr/learn/courses/30/lessons/12954",

            //중
            "https://programmers.co.kr/learn/courses/30/lessons/82612",
            "https://programmers.co.kr/learn/courses/30/lessons/12901",//여기서 부터 시작
            "https://programmers.co.kr/learn/courses/30/lessons/12916",

            //상
            "https://programmers.co.kr/learn/courses/30/lessons/68935",
            "https://programmers.co.kr/learn/courses/30/lessons/12906",
            "https://programmers.co.kr/learn/courses/30/lessons/77484"

    };

    private static final String[] languages = {"java", "javascript", "python3"};

    @Before
    public void setDriverSetting() {
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

//    @Test
//    @DisplayName("문제 출력")
//    public void 문제_및_언어_템플릿_가져오기() {
//        int cnt = 0;
//        List<Question> list = new ArrayList<>();
//
//        for (String url : QuestionUrl) {
//            //given
//            boolean isGetQuestions = true;
//            Question question = null;
//            //문제 제목
//            String title = null;
//            //문제 설명
//            String questionDescription= null;
//            //제한 조건
//            String limitation= null;
//            //입출력 예
//            String inOutExHead= null;
//            String inOutEx= null;
//            //입출력 예 설명
//            String inOutExDescription= null;
//            //참고 사항
//            String reference= null;
//            //난이도
//            String level= null;
//            Map<String, String> codesTemplate = new HashMap<>();
//
//            try {
//                //세션 시작
//                driver = new ChromeDriver(options);
//                //when
//                for (String language : languages) {
//
//                    driver.get(url + "?language=" + language);
//
//                    //한번만 문제 가져오기
//                    if (isGetQuestions) {
//                        isGetQuestions = false;
//
//
//                       title = driver.findElement(By.xpath("//*[@id=\"tab\"]/li")).getText();
//
//                        questionDescription = driver.findElement(By.xpath("//*[@id=\"tour2\"]/div/div/p[1]")).getText();
//
//                        limitation = driver.findElement(By.xpath("//*[@id=\"tour2\"]/div/div/ul")).getText();
//
//
//                        try {
//                            inOutExHead = driver.findElement(By.xpath("//*[@id=\"tour2\"]/div/div/table/thead")).getText();
//                            inOutEx = driver.findElement(By.xpath("//*[@id=\"tour2\"]/div/div/table/tbody")).getText();
//                        } catch (NoSuchElementException e) {
//                            inOutExHead = "";
//                            inOutEx = driver.findElement(By.xpath("//*[@id=\"tour2\"]/div/div/p[2]")).getText();
//                        }
//
//
//
//                        try {
//                            inOutExDescription = driver.findElement(By.xpath("//*[@id=\"tour2\"]/div/div/p[2]")).getText();
//                        } catch (NoSuchElementException e) {
//                            inOutExDescription = "No Info";
//                        }
//
//                        try {
//                            reference = driver.findElement(By.xpath("//*[@id=\"tour2\"]/div/div/ul[2]")).getText();
//                        } catch (NoSuchElementException e) {
//                            reference = "No Info";
//                        }
//
//
//                        if (cnt < 4) {
//                            level = "하";
//                        } else if (6 < cnt) {
//                            level = "상";
//                        } else {
//                            level = "중";
//                        }
//
//                        cnt++;
//                    }
//
//                    //코드 태그 목록 추출, 합치기
//                    StringBuilder buffer = new StringBuilder();
//                    List<WebElement> solutions = driver.findElements(By.className("CodeMirror-line")); //모든 코드 줄 가져오기
//
//                    for (WebElement solution : solutions) {
//                        String eachLine = solution.findElement(By.tagName("span")).getText();
//                        buffer.append(eachLine);
//
//                    }
//                    codesTemplate.put(language, buffer.toString());
//                }
//
//                //객체 생성
//                question = Question.builder()
//                        .title(title)
//                        .questionDescription(questionDescription)
//                        .limitation(limitation)
//                        .inOutExHead(inOutExHead)
//                        .inOutEx(inOutEx)
//                        .inOutExDescription(inOutExDescription)
//                        .reference(reference)
//                        .level(level)
//                        .templates(codesTemplate)
//                        .build();
//
//            } finally {
//                driver.quit();
//            }
//            list.add(question);
//            //여기에서 맵데이터 엔티티에 넣기
//            question.templates = codesTemplate;
//        }
//        for (Question question : list) {
//            System.out.println(question.toString());
//        }
//    }

    @Test
    @DisplayName("기본 코드 템플릿")
    public void getSolutions() {

        driver = new ChromeDriver(options);
        List<String> questions = new ArrayList<>();
        try {
            String[] languages = {"java", "javascript", "python3"}; //가져올 언어
            for (String language : languages) {

                //문제 url
                String url = "https://programmers.co.kr/learn/courses/30/lessons/12969?language=";
                driver.get(url + language);

                //코드 태그 목록 추출, 합치기
                StringBuilder buffer = new StringBuilder();
                List<WebElement> solutions = driver.findElements(By.className("CodeMirror-line")); //모든 코드 줄 가져오기

                for (WebElement solution : solutions) {
                    String eachLine = solution.findElement(By.tagName("span")).getText();
                    buffer.append(eachLine);

                }
                questions.add(buffer.toString());
            }
        } finally {
            driver.close();
        }
        System.out.println(questions);
    }

    @Test
    @DisplayName("크롤링 코드 리팩토링")
    public void test(){
        List<Question> list = new ArrayList<>();
        QuestionLevel level;
        for (int i=0; i< QuestionUrl.length;i++) {
            if (i < 4) {
                level = QuestionLevel.EASY;
            } else if (6 < i) {
                level = QuestionLevel.HARD;
            } else {
                level = QuestionLevel.NORMAL;
            }
            Question question = uploadQuestion(QuestionUrl[i], level);
            list.add(question);
        }
        for (Question question : list) {
            System.out.println(question.toString());
        }
    }

    public Question uploadQuestion(String questionUrl, QuestionLevel level) {
        //given
        boolean isGetQuestions = true;
        com.seventeam.algoritmgameproject.domain.model.Question question = null;
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
                //when


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
                .questionDescription(questionDescription)
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

@NoArgsConstructor(access = AccessLevel.PRIVATE)
@ToString
class Question {
    String title;
    //문제 설명
    String questionDescription;
    //제한 조건
    String limitation;
    //입출력 예 header
    String inOutExHead;
    //입출력 예 Body
    String inOutEx;
    //입출력 예 설명(null 허용)
    String inOutExDescription;
    //참고 사항(null 허용)
    String reference;
    //난이도
    QuestionLevel level;
    // 언어별 문제 기본 템플릿
    Map<String, String> templates = new HashMap<>();

    @Builder
    public Question(String title, String questionDescription, String limitation, String inOutExHead, String inOutEx, String inOutExDescription, String reference, QuestionLevel level, Map<String, String> templates) {
        this.title = title;
        this.questionDescription = questionDescription;
        this.limitation = limitation;
        this.inOutExHead = inOutExHead;
        this.inOutEx = inOutEx;
        this.inOutExDescription = inOutExDescription;
        this.reference = reference;
        this.level = level;
        this.templates = templates;
    }
}