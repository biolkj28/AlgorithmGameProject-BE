package com.seventeam.algoritmgameproject.web.service.compiler_service;

import com.seventeam.algoritmgameproject.domain.model.questions.TestCase;
import com.seventeam.algoritmgameproject.domain.model.login.User;
import com.seventeam.algoritmgameproject.web.repository.questions_repository.TestCaseDslRepository;
import com.seventeam.algoritmgameproject.web.repository.UserRepository;
import com.seventeam.algoritmgameproject.web.dto.compiler_dto.CompileRequestDto;
import com.seventeam.algoritmgameproject.web.dto.compiler_dto.CompileResultDto;
import com.seventeam.algoritmgameproject.web.service.compiler_service.generatedTemplate.GeneratedTemplate;
import com.seventeam.algoritmgameproject.web.service.game_service.GameProcess;
import com.seventeam.algoritmgameproject.web.service.game_service.GameService;
import com.seventeam.algoritmgameproject.web.repository.game_repository.GameSessionRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.json.simple.JSONObject;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Slf4j
@Service
@RequiredArgsConstructor
public class CompilerService {
    private final Map<String, GeneratedTemplate> templateMap;
    private final TestCaseDslRepository repository;
    private final JDoodleApi jDoodleApi;
    private final GameProcess service;
    private static final Map<String, Boolean> compileRequestMap = new ConcurrentHashMap<>();;
    private final UserRepository userRepository;
    private final GameSessionRepository sessionRepository;


    public CompileResultDto compileResult(CompileRequestDto dto, User user) {
        if (!compileRequestMap.containsKey(dto.getRoomId())) {
            compileRequestMap.put(dto.getRoomId(), true);
        } else {
            return CompileResultDto.builder()
                    .msg("상대 사용자가 컴파일 중입니다.")
                    .result(false)
                    .build();
        }

        GeneratedTemplate template = null;
        Language language = null;
        switch (dto.getLanguageIdx()) {
            case 0: {
                template = templateMap.get("generatedJavaTemplateImp");
                language = Language.JAVA;
                break;
            }
            case 1: {
                template = templateMap.get("generatedJavascriptTemplateImp");
                language = Language.NODEJS;
                break;
            }
            case 2: {
                template = templateMap.get("generatedPython3TemplateImp");
                language = Language.PYTHON3;
                break;
            }
        }

        List<TestCase> testCases = repository.getTestCases(dto.getQuestionId());
        if (template == null) {
            throw new RuntimeException("해당 언어는 지원하지 않습니다");
        }

        JSONObject compile = jDoodleApi.compile(template.compileCode(dto.getCodeStr(), testCases, dto.getQuestionId()), language);
        String output = compile.get("output").toString();
        log.info(output);

        if (output.contains("error") || output.contains("ReferenceError") || output.contains("Traceback")) {

            return errorResult(output, dto, user);

        } else if (output.contains("No \"public class\" found to execute")) {

            return publicClassErrorResult(dto, user);

        } else {

            return compileCheckResult(output, testCases, language, dto, user);
        }
    }

    public CompileResultDto compileCheckResult(String output, List<TestCase> testCases, Language language, CompileRequestDto dto, User user) {
        String[] ans = output.replace("\n", "").split("_");
        int cnt = 0;
        boolean resultBool = true;
        String msg;
        //테스트케이스의 개수로 퍼센트지 계산을 위한 변수
        int divide = (100 / testCases.size());
        for (int i = 0; i < ans.length; i++) {
            String answer = testCases.get(i).getAnswer().replaceAll("\\p{Z}", "");
            StringBuilder result = new StringBuilder(ans[i].replaceAll("\\p{Z}", ""));

            if (0 < answer.indexOf("[") && !language.equals(Language.JAVA)) {
                result.insert(0, "[");
                result.append("]");
            }

            if (answer.equals(result.toString())) {
                cnt += divide;
            }
        }
        if (cnt < 100) {
            resultBool = false;
            msg = "오답";
            service.compileFail(user.getUserId(),dto.getRoomId());
        } else {
            winProcess(user);
            service.loseMessage(user.getUserId(),dto.getRoomId());
            loseProcess(dto.getRoomId(), user);
            msg = "승리";
        }
        //컴파일 동시성 문제 방지
        compileRequestMap.remove(dto.getRoomId());
        return CompileResultDto.builder()
                .msg(msg)
                .result(resultBool)
                .build();

    }

    public CompileResultDto errorResult(String output, CompileRequestDto dto, User user) {

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

        compileRequestMap.remove(dto.getRoomId());
        service.compileFail(user.getUserId(),dto.getRoomId());

        return CompileResultDto.builder()
                .msg(errorMsg.toString())
                .result(false)
                .build();
    }

    public CompileResultDto publicClassErrorResult(CompileRequestDto dto, User user) {
        compileRequestMap.remove(dto.getRoomId());
        service.compileFail(user.getUserId(),dto.getRoomId());
        return CompileResultDto.builder()
                .msg("No \"public class\" found to execute")
                .result(false)
                .build();
    }

    @Transactional
    public void winProcess(User user) {
        User winUser = userRepository.findByUserId(user.getUserId()).orElseThrow(() -> new NullPointerException("없는 유저 입니다."));
        winUser.win();
        userRepository.save(winUser);
    }

    @Transactional
    public void loseProcess(String roomId, User user) {
        String othersSession = sessionRepository.findOthersSession(roomId, user.getUserId());
        User loseUser = userRepository.findByUserId(othersSession).orElseThrow(() -> new NullPointerException("없는 유저 입니다."));
        loseUser.win();
        userRepository.save(loseUser);
    }

}
