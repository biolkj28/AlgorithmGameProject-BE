//package com.seventeam.algoritmgameproject.service.compilerService;
//
//;
//import com.seventeam.algoritmgameproject.domain.model.login.User;
//import com.seventeam.algoritmgameproject.service.compilerService.generatedTemplate.GeneratedJavaTemplateImpTest;
//import com.seventeam.algoritmgameproject.service.compilerService.generatedTemplate.QuestionStrToJS;
//import com.seventeam.algoritmgameproject.service.compilerService.generatedTemplate.QuestionStrToPython;
//import com.seventeam.algoritmgameproject.service.compilerService.generatedTemplate.QuestionsStr;
//import com.seventeam.algoritmgameproject.service.crawlingService.Solution;
//import com.seventeam.algoritmgameproject.web.dto.compiler_dto.CompileRequestDto;
//import com.seventeam.algoritmgameproject.web.dto.compiler_dto.CompileResultDto;
//import com.seventeam.algoritmgameproject.web.dto.questions_dto.TestCaseRedis;
//import com.seventeam.algoritmgameproject.web.repository.UserRepository;
//import com.seventeam.algoritmgameproject.web.repository.questions_repository.TestCaseDslRepository;
//import com.seventeam.algoritmgameproject.web.service.compiler_service.CompilerService;
//import com.seventeam.algoritmgameproject.web.service.compiler_service.CompilerServiceImp;
//import com.seventeam.algoritmgameproject.web.service.compiler_service.JDoodleApi;
//import com.seventeam.algoritmgameproject.web.service.compiler_service.generatedTemplate.GeneratedTemplate;
//import lombok.extern.slf4j.Slf4j;
//import org.junit.jupiter.api.DisplayName;
//import org.junit.jupiter.api.Test;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.context.SpringBootTest;
//
//import java.util.ArrayList;
//import java.util.List;
//import java.util.Map;
//
//
//@SpringBootTest
//@Slf4j
//public class OnlineCompilerIntegrationTest {
//
//
//    @Autowired
//    private TestCaseDslRepository repository;
//
//    @Autowired
//    private JDoodleApi service;
//    @Autowired
//    Map<String, GeneratedTemplate> beans;
//
//    @Autowired
//    UserRepository userRepository;
//
//    @Autowired
//    CompilerServiceImp compilerService;
//
//    @Test
//    @DisplayName("자바 컴파일 테스트")
//    void 자바() {
//        List<CompileRequestDto> java = new ArrayList<>();
//        List<CompileRequestDto> js = new ArrayList<>();
//        List<CompileRequestDto> py = new ArrayList<>();
//
//        java.add(new CompileRequestDto("test", 1L, 0, QuestionsStr.code1));
//        js.add(new CompileRequestDto("test", 1L, 1, QuestionStrToJS.code1));
//        py.add(new CompileRequestDto("test", 1L, 2, QuestionStrToPython.code1));
//
//        java.add(new CompileRequestDto("test", 14L, 0, QuestionsStr.code2));
//        js.add(new CompileRequestDto("test", 14L, 1, QuestionStrToJS.code2));
//        py.add(new CompileRequestDto("test", 14L, 2, QuestionStrToPython.code2));
//
//        java.add(new CompileRequestDto("test", 26L, 0, QuestionsStr.code3));
//        js.add(new CompileRequestDto("test", 26L, 1, QuestionStrToJS.code3));
//        py.add(new CompileRequestDto("test", 26L, 2, QuestionStrToPython.code3));
//
//        java.add(new CompileRequestDto("test", 37L, 0, QuestionsStr.code4));
//        js.add(new CompileRequestDto("test", 37L, 1, QuestionStrToJS.code4));
//        py.add(new CompileRequestDto("test", 37L, 2, QuestionStrToPython.code4));
//
//        java.add(new CompileRequestDto("test", 48L, 0, QuestionsStr.code5));
//        js.add(new CompileRequestDto("test", 48L, 1, QuestionStrToJS.code5));
//        py.add(new CompileRequestDto("test", 48L, 2, QuestionStrToPython.code5));
//
//        java.add(new CompileRequestDto("test", 60L, 0, QuestionsStr.code6));
//        js.add(new CompileRequestDto("test", 60L, 1, QuestionStrToJS.code6));
//        py.add(new CompileRequestDto("test", 60L, 2, QuestionStrToPython.code6));
//
//        java.add(new CompileRequestDto("test", 72L, 0, QuestionsStr.code7));
//        js.add(new CompileRequestDto("test", 72L, 1, QuestionStrToJS.code7));
//        py.add(new CompileRequestDto("test", 72L, 2, QuestionStrToPython.code7));
//
//        java.add(new CompileRequestDto("test", 85L, 0, QuestionsStr.code8));
//        js.add(new CompileRequestDto("test", 85L, 1, QuestionStrToJS.code8));
//        py.add(new CompileRequestDto("test", 85L, 2, QuestionStrToPython.code8));
//
//
//        java.add(new CompileRequestDto("test", 98L, 0, QuestionsStr.code9));
//        js.add(new CompileRequestDto("test", 98L, 1, QuestionStrToJS.code9));
//        py.add(new CompileRequestDto("test", 98L, 2, QuestionStrToPython.code9));
//
//        User user = userRepository.findByUserId("biolkj28").orElseThrow(() -> new NullPointerException("anjwl"));
//        for (int i = 0; i < 9; i++) {
//            CompileResultDto compileResultDto = compilerService.compileResult(java.get(i), user);
//            CompileResultDto compileResultDto1 = compilerService.compileResult(js.get(i), user);
//            CompileResultDto compileResultDto2 = compilerService.compileResult(py.get(i), user);
//            log.info(compileResultDto.toString());
//            log.info(compileResultDto1.toString());
//            log.info(compileResultDto2.toString());
//        }
//
//
////        List<TestCaseRedis> testCases = compilerService.getTestCases(1L);
////        List<TestCaseRedis> testCases1 = compilerService.getTestCases(14L);
////        List<TestCaseRedis> testCases2 = compilerService.getTestCases(26L);
////        List<TestCaseRedis> testCases3 = compilerService.getTestCases(37L);
////        List<TestCaseRedis> testCases4 = compilerService.getTestCases(48L);
////        List<TestCaseRedis> testCases5 = compilerService.getTestCases(60L);
////        List<TestCaseRedis> testCases6 = compilerService.getTestCases(72L);
////        List<TestCaseRedis> testCases7 = compilerService.getTestCases(85L);
////        List<TestCaseRedis> testCases8 = compilerService.getTestCases(98L);
////
////        log.info("크기:{}",testCases.size());
////        log.info("크기:{}",testCases1.size());
////        log.info("크기:{}",testCases2.size());
////        log.info("크기:{}",testCases3.size());
////        log.info("크기:{}",testCases4.size());
////        log.info("크기:{}",testCases5.size());
////        log.info("크기:{}",testCases6.size());
////        log.info("크기:{}",testCases7.size());
////        log.info("크기:{}",testCases8.size());
//
////        testCases.forEach(t -> System.out.println(t));
////        testCases1.forEach(t -> System.out.println(t));
////        testCases2.forEach(t -> System.out.println(t));
////        testCases3.forEach(t -> System.out.println(t));
////        testCases4.forEach(t -> System.out.println(t));
////        testCases5.forEach(t -> System.out.println(t));
////        testCases6.forEach(t -> System.out.println(t));
////        testCases7.forEach(t -> System.out.println(t));
////        testCases8.forEach(t -> System.out.println(t));
//
//
//    }
//}
////  @Test
////  @DisplayName("자바스크립트 코드 문자열 추가 확인 테스트")
////   void 자바스크립트문자열추가() throws InterruptedException, ScriptException {
////        GeneratedJavascriptTemplateImpTest js = new GeneratedJavascriptTemplateImpTest(repository);
////
////        compile(js.compileCode(QuestionStrToJS.code1, 125L));
////
////        ScriptEngine engine = new ScriptEngineManager().getEngineByName("nashorn"); //  --- (1)
////        engine.eval(js.compileCode(QuestionStrToJS.code1, 125L));
////        log.info(js.compileCode(QuestionStrToJS.code2, 138L));
////        log.info(js.compileCode(QuestionStrToJS.code3, 150L));
////        log.info(js.compileCode(QuestionStrToJS.code4, 161L));
////        log.info(js.compileCode(QuestionStrToJS.code5, 172L));
////        log.info(js.compileCode(QuestionStrToJS.code6, 184L));
////        log.info(js.compileCode(QuestionStrToJS.code7, 196L));
////        log.info(js.compileCode(QuestionStrToJS.code8, 209L));
////        log.info(js.compileCode(QuestionStrToJS.code9, 222L));
////   }
////
////   public void compile(String codeStr) throws InterruptedException {
////        try (Context context = Context.create("js")) {
////            Value js = context.eval("js", codeStr);
////            System.out.println("값:" + js.toString());
////        } catch (Exception e) {
////            System.out.println(e.getMessage());
////        }
////
////   }
////
////   @Test
////  @DisplayName("자바스크립트 컴파일 테스트")
////   void 자바스크립트() {
////        GeneratedJavascriptTemplateImpTest js = new GeneratedJavascriptTemplateImpTest(repository);
////
////        log.info(service.compile(js.compileCode(QuestionStrToJS.code1, 125L), Language.NODEJS));
////        log.info(service.compile(js.compileCode(QuestionStrToJS.code2, 138L), Language.NODEJS));
////        log.info(service.compile(js.compileCode(QuestionStrToJS.code3, 150L), Language.NODEJS));
////        log.info(service.compile(js.compileCode(QuestionStrToJS.code4, 161L), Language.NODEJS));
////        log.info(service.compile(js.compileCode(QuestionStrToJS.code5, 172L), Language.NODEJS));
////        log.info(service.compile(js.compileCode(QuestionStrToJS.code6, 184L), Language.NODEJS));
////        log.info(service.compile(js.compileCode(QuestionStrToJS.code7, 196L), Language.NODEJS));
////        log.info(service.compile(js.compileCode(QuestionStrToJS.code8, 209L), Language.NODEJS));
////        log.info(service.compile(js.compileCode(QuestionStrToJS.code9, 222L), Language.NODEJS));
////
////    }
////
////   @Test
////   @DisplayName("파이썬 컴파일 코드 문자열 확인 테스트")
////   void 파이썬코드템플릿() throws InterruptedException {
////        GeneratedPythonTemplateImpTest py = new GeneratedPythonTemplateImpTest(repository);
////        compile(py.compileCode(QuestionStrToPython.code1, 125L));
////        log.info(py.compileCode(QuestionStrToPython.code1, 125L));
////        log.info(py.compileCode(QuestionStrToPython.code2, 138L));
////        log.info(py.compileCode(QuestionStrToPython.code3, 150L));
////        log.info(py.compileCode(QuestionStrToPython.code4, 161L));
////        log.info(py.compileCode(QuestionStrToPython.code5, 172L));
////        log.info(py.compileCode(QuestionStrToPython.code6, 184L));
////        log.info(py.compileCode(QuestionStrToPython.code7, 196L));
////        log.info(py.compileCode(QuestionStrToPython.code8, 209L));
////        log.info(py.compileCode(QuestionStrToPython.code9, 222L));
////   }
////
////   @Test
////   @DisplayName("파이썬 컴파일 테스트")
////    void 파이썬() {
////
////
////          GeneratedPythonTemplateImpTest py = new GeneratedPythonTemplateImpTest(repository);
////         log.info(service.compile(pyStr.compileCode(QuestionStrToPython.code1, 125L), Language.PYTHON3));
////        log.info(service.compile(py.compileCode(QuestionStrToPython.code2, 138L), Language.PYTHON3));
////        log.info(service.compile(py.compileCode(QuestionStrToPython.code3, 150L), Language.PYTHON3));
////        log.info(service.compile(py.compileCode(QuestionStrToPython.code4, 161L), Language.PYTHON3));
////        log.info(service.compile(py.compileCode(QuestionStrToPython.code5, 172L), Language.PYTHON3));
////        log.info(service.compile(py.compileCode(QuestionStrToPython.code6, 184L), Language.PYTHON3));
////        log.info(service.compile(py.compileCode(QuestionStrToPython.code7, 196L), Language.PYTHON3));
////        log.info(service.compile(py.compileCode(QuestionStrToPython.code8, 209L), Language.PYTHON3));
////        log.info(service.compile(py.compileCode(QuestionStrToPython.code9, 222L), Language.PYTHON3));
////
////   }
////
////    @Test
////    @DisplayName("오류 출력 테스트")
////    void completePer() {
////        String roomId1 = UUID.randomUUID().toString();
//////        String roomId2 = UUID.randomUUID().toString();
//////        String roomId3 = UUID.randomUUID().toString();
////        long questionId = 1L;
////        CompileRequestDto dto0 = new CompileRequestDto(roomId1, questionId, 0, QuestionsStr.code1);
//////        CompileRequestDto dto1 = new CompileRequestDto(roomId2, questionId, 1, QuestionStrToJS.code1);
//////        CompileRequestDto dto2 = new CompileRequestDto(roomId3, questionId, 2, QuestionStrToPython.code1);
////        User user = User.builder()
////                .userId("novem")
////                .avatarUrl("no_info")
////                .password("1234")
////                .build();
////
////        //자바
////        String s = compilerService.compileResult(dto0,user).toString();
////        log.info(s);
////        //자바스크립트
////        String s1 = compilerService.compileResult(dto1,user).toString();
////        log.info(s1);
////
////        String s2 = compilerService.compileResult(dto2,user).toString();
////        log.info(s2);
//// }
////
//
////    public String compileResult(GeneratedTemplate template, Long questionId, Language lang, String codeStr) {
////
////        List<TestCase> testCases = repository.getTestCases(questionId);
////        JSONObject compile = service.compile(template.compileCode(codeStr, testCases,questionId), lang);
////        String output = compile.get("output").toString();
////
////        if (output.contains("error") || output.contains("ReferenceError") || output.contains("Traceback")) {
////            int len = output.length();
////            StringBuilder out = new StringBuilder(output);
////
////            out.deleteCharAt(0);
////            out.deleteCharAt(len - 2);
////            output = out.toString();
////
////            String[] split = output.split("\n");
////            StringBuilder errorMsg = new StringBuilder(50);
////            for (String s : split) {
////                s = s
////                        .replace(" ", "&#32;")
////                        .replace("/", "&#47;")
////                        .replace("^", "");
////
////                errorMsg.append(s.trim());
////                errorMsg.append("<br>");
////            }
////            return errorMsg.toString();
////        } else {
////            String[] ans = output.replace("\n", "").split("_");
////            int cnt = 0;
////            //테스트케이스의 개수로 퍼센트지 계산을 위한 변수
////            int divide = (100 / testCases.size());
////            for (int i = 0; i < ans.length; i++) {
////                String answer = testCases.get(i).getAnswer().replaceAll("\\p{Z}", "");
////                StringBuilder result = new StringBuilder(ans[i].replaceAll("\\p{Z}", ""));
////                if (0 < answer.indexOf("[") && !lang.equals(Language.JAVA)) {
////                    result.insert(0, "[");
////                    result.append("]");
////                }
////                System.out.println(answer + "==" + result);
////                if (answer.equals(result.toString())) {
////                    cnt += divide;
////                }
////            }
////            return "정확도: " + cnt + "%";
////        }
////    }
//
////}
