package com.seventeam.algoritmgameproject.web.service.compiler_service;

import com.seventeam.algoritmgameproject.domain.model.login.User;
import com.seventeam.algoritmgameproject.domain.model.questions.TestCase;
import com.seventeam.algoritmgameproject.web.dto.compiler_dto.CompileRequestDto;
import com.seventeam.algoritmgameproject.web.dto.compiler_dto.CompileResultDto;

import java.util.List;

public interface CompilerService {
    CompileResultDto compileResult(CompileRequestDto dto, User user);

    CompileResultDto compileCheckResult(String output, List<TestCase> testCases, Language language, CompileRequestDto dto, User user);

    CompileResultDto errorResult(String output, CompileRequestDto dto, User user);

    CompileResultDto publicClassErrorResult(CompileRequestDto dto, User user);
}
