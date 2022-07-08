package com.seventeam.algoritmgameproject.web.service.compilerService;

import com.seventeam.algoritmgameproject.domain.model.TestCase;
import com.seventeam.algoritmgameproject.domain.repository.TestCaseDslRepository;
import com.seventeam.algoritmgameproject.web.dto.CompileResultDto;
import com.seventeam.algoritmgameproject.web.service.compilerService.generatedTemplate.GeneratedTemplate;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.json.simple.JSONObject;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
@Slf4j
@Service
@RequiredArgsConstructor
public class CompilerService {
    private final Map<String,GeneratedTemplate>templateMap;
    private final TestCaseDslRepository repository;
    private final JDoodleApi jDoodleApi;

    public CompileResultDto compileResult(Long questionId, Language lang, String codeStr) {

        GeneratedTemplate template=null;
        switch (lang.getValue()){
            case "java":{
                template = templateMap.get("generatedJavaTemplateImp");
                break;
            }
            case "nodejs":{
                template = templateMap.get("generatedJavascriptTemplateImp");
                break;
            }
            case "python3":{
                template = templateMap.get("generatedPython3TemplateImp");
                break;
            }
        }

        List<TestCase> testCases = repository.getTestCases(questionId);
        if(template==null){
            throw new RuntimeException("해당 언어는 지원하지 않습니다");
        }
        JSONObject compile = jDoodleApi.compile(template.compileCode(codeStr, testCases), lang);
        String output = compile.get("output").toString();

        if (output.contains("error") || output.contains("ReferenceError") || output.contains("Traceback")) {
            int len = output.length();
            StringBuilder out = new StringBuilder(output);

            out.deleteCharAt(0);
            out.deleteCharAt(len - 2);
            output = out.toString();

            String[] split = output.split("\n");
            StringBuilder errorMsg = new StringBuilder(50);
            for (String s : split) {
                s = s
                        .replace(" ", "&#32;")
                        .replace("/", "&#47;")
                        .replace("^", "");

                errorMsg.append(s.trim());
                errorMsg.append("<br>");
            }

            return CompileResultDto.builder()
                    .msg(errorMsg.toString())
                    .result(false)
                    .build();
        } else {
            String[] ans = output.replace("\n", "").split("_");
            int cnt = 0;
            boolean resultBool = true;
            //테스트케이스의 개수로 퍼센트지 계산을 위한 변수
            int divide = (100 / testCases.size());
            for (int i = 0; i < ans.length; i++) {
                String answer = testCases.get(i).getAnswer().replaceAll("\\p{Z}", "");
                StringBuilder result = new StringBuilder(ans[i].replaceAll("\\p{Z}", ""));

                if (0 < answer.indexOf("[") && !lang.equals(Language.JAVA)) {
                    result.insert(0, "[");
                    result.append("]");
                }

                if (answer.equals(result.toString())) {
                    cnt += divide;
                }
            }
            if(cnt!=100){
                resultBool = false;
            }
            return CompileResultDto.builder()
                    .msg("정확도: " + cnt + "%")
                    .result(resultBool)
                    .build();
        }
    }
}
