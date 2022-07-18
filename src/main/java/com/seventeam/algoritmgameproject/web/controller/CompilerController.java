package com.seventeam.algoritmgameproject.web.controller;

import com.seventeam.algoritmgameproject.config.HttpCode;
import com.seventeam.algoritmgameproject.web.dto.CompileRequestDto;
import com.seventeam.algoritmgameproject.web.dto.CompileResultDto;
import com.seventeam.algoritmgameproject.web.service.compilerService.CompilerService;
import com.seventeam.algoritmgameproject.web.socketServer.Dto.CreateRoomRequestDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class CompilerController {

    private final CompilerService service;

    @PostMapping("/api/compile")
    @Operation(summary = "컴파일 동작")
    @ApiResponses(value = {
            @ApiResponse(responseCode = HttpCode.HTTPSTATUS_OK, description = "컴파일 완료",
                    content = {@Content(mediaType = "application/json", schema = @Schema(implementation = CompileResultDto.class))}),
            @ApiResponse(responseCode = HttpCode.HTTPSTATUS_FORBIDDEN, description = "로그인 필요", content = @Content),
            @ApiResponse(responseCode = HttpCode.HTTPSTATUS_BADREQUEST, description = "요청 데이터 오류", content = @Content),
            @ApiResponse(responseCode = HttpCode.HTTPSTATUS_SERVERERROR, description = "서버 오류", content = @Content)
    })
    @Parameter(in = ParameterIn.PATH,name = "CompileRequestDto",
            content = {@Content(mediaType = "application/json", schema = @Schema(implementation = CompileRequestDto.class))} )
    public ResponseEntity<?> compile(
            @RequestBody CompileRequestDto dto) {
        return new ResponseEntity<>(
                service.compileResult(
                        dto.getQuestionId(),
                        dto.getLanguageIdx(),
                        dto.getCodeStr()),
                HttpStatus.OK);
    }

}
