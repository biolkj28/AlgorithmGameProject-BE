package com.seventeam.algoritmgameproject.service.compilerService;

import com.seventeam.algoritmgameproject.domain.repository.TestCaseDslRepository;
import com.seventeam.algoritmgameproject.service.compilerService.generatedTemplate.*;
import com.seventeam.algoritmgameproject.web.service.compilerService.Language;
import com.seventeam.algoritmgameproject.web.service.compilerService.OnlineCompilerService;
import com.seventeam.algoritmgameproject.web.service.compilerService.generatedTemplate.GeneratedPython3TemplateImp;
import lombok.extern.slf4j.Slf4j;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
@Slf4j
public class OnlineCompilerIntegrationTest {


    @Autowired
    private TestCaseDslRepository repository;

    @Autowired
    private OnlineCompilerService service;

    @Test
    @DisplayName("자바 컴파일 테스트")
    void 자바() {
        GeneratedJavaTemplateImpTest java = new GeneratedJavaTemplateImpTest(repository);

        //log.info(java.compileCode(QuestionsStr.code1, 125L));
//        log.info(service.compile(compileCode(QuestionsStr.code2,138L)));
//        log.info(service.compile(compileCode(QuestionsStr.code3,150L)));
//        log.info(service.compile(compileCode(QuestionsStr.code4,161L)));
//        log.info(service.compile(compileCode(QuestionsStr.code5,172L)));
//        log.info(service.compile(compileCode(QuestionsStr.code6,184L)));
//        log.info(service.compile( compileCode(QuestionsStr.code7,196L)));
//        log.info(service.compile(compileCode(QuestionsStr.code8,209L)));
//        log.info(service.compile(compileCode(QuestionsStr.code9,222L)));

    }
    @Test
    @DisplayName("자바스크립트 코드 문자열 추가 확인 테스트")
    void 자바스크립트문자열추가() {
        GeneratedJavascriptTemplateImpTest js = new GeneratedJavascriptTemplateImpTest(repository);

        log.info(js.compileCode(QuestionStrToJS.code1, 125L));
        log.info(js.compileCode(QuestionStrToJS.code2, 138L));
        log.info(js.compileCode(QuestionStrToJS.code3, 150L));
        log.info(js.compileCode(QuestionStrToJS.code4, 161L));
        log.info(js.compileCode(QuestionStrToJS.code5, 172L));
        log.info(js.compileCode(QuestionStrToJS.code6, 184L));
        log.info(js.compileCode(QuestionStrToJS.code7, 196L));
        log.info(js.compileCode(QuestionStrToJS.code8, 209L));
        log.info(js.compileCode(QuestionStrToJS.code9, 222L));
    }

    @Test
    @DisplayName("자바스크립트 컴파일 테스트")
    void 자바스크립트() {
        GeneratedJavascriptTemplateImpTest js = new GeneratedJavascriptTemplateImpTest(repository);

        log.info(service.compile(js.compileCode(QuestionStrToJS.code1, 125L),Language.NODEJS));
        log.info(service.compile(js.compileCode(QuestionStrToJS.code2, 138L),Language.NODEJS));
        log.info(service.compile(js.compileCode(QuestionStrToJS.code3, 150L),Language.NODEJS));
        log.info(service.compile(js.compileCode(QuestionStrToJS.code4, 161L),Language.NODEJS));
        log.info(service.compile(js.compileCode(QuestionStrToJS.code5, 172L),Language.NODEJS));
        log.info(service.compile(js.compileCode(QuestionStrToJS.code6, 184L),Language.NODEJS));
        log.info(service.compile(js.compileCode(QuestionStrToJS.code7, 196L),Language.NODEJS));
        log.info(service.compile(js.compileCode(QuestionStrToJS.code8, 209L),Language.NODEJS));
        log.info(service.compile(js.compileCode(QuestionStrToJS.code9, 222L),Language.NODEJS));

    }

    @Test
    @DisplayName("파이썬 컴파일 코드 문자열 확인 테스트")
    void 파이썬코드템플릿() {
        GeneratedPythonTemplateImpTest py = new GeneratedPythonTemplateImpTest(repository);
        log.info(py.compileCode(QuestionStrToPython.code1, 125L));
        log.info(py.compileCode(QuestionStrToPython.code2, 138L));
        log.info(py.compileCode(QuestionStrToPython.code3, 150L));
        log.info(py.compileCode(QuestionStrToPython.code4, 161L));
        log.info(py.compileCode(QuestionStrToPython.code5, 172L));
        log.info(py.compileCode(QuestionStrToPython.code6, 184L));
        log.info(py.compileCode(QuestionStrToPython.code7, 196L));
        log.info(py.compileCode(QuestionStrToPython.code8, 209L));
        log.info(py.compileCode(QuestionStrToPython.code9, 222L));
    }

    @Test
    @DisplayName("파이썬 컴파일 테스트")
    void 파이썬() {

        GeneratedPythonTemplateImpTest py = new GeneratedPythonTemplateImpTest(repository);
        log.info(service.compile(py.compileCode(QuestionStrToPython.code1, 125L), Language.PYTHON3));
        log.info(service.compile(py.compileCode(QuestionStrToPython.code2, 138L), Language.PYTHON3));
        log.info(service.compile(py.compileCode(QuestionStrToPython.code3, 150L), Language.PYTHON3));
        log.info(service.compile(py.compileCode(QuestionStrToPython.code4, 161L), Language.PYTHON3));
        log.info(service.compile(py.compileCode(QuestionStrToPython.code5, 172L), Language.PYTHON3));
        log.info(service.compile(py.compileCode(QuestionStrToPython.code6, 184L), Language.PYTHON3));
        log.info(service.compile(py.compileCode(QuestionStrToPython.code7, 196L), Language.PYTHON3));
        log.info(service.compile(py.compileCode(QuestionStrToPython.code8, 209L), Language.PYTHON3));
        log.info(service.compile(py.compileCode(QuestionStrToPython.code9, 222L), Language.PYTHON3));

    }

}
