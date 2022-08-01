package com.seventeam.algoritmgameproject.security.controller;


import com.seventeam.algoritmgameproject.domain.model.OAuthToken;
import com.seventeam.algoritmgameproject.security.dto.UserResponseDto;
import com.seventeam.algoritmgameproject.security.service.GithubLoginService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.env.Environment;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;
import java.util.Arrays;

@Slf4j
@RestController
@RequiredArgsConstructor
public class OAuthController {
    private final GithubLoginService service;
    private final Environment env;
    @GetMapping("/login/oauth2/code/github")
    public UserResponseDto githubLogin(String code) {
        OAuthToken oAuthToken = service.getOAuthToken(code);
        return service.getGithubProfile(oAuthToken);
    }

    @GetMapping("/profile")
    @Operation(summary = "무중단 배포 확인용(무시해도됨)")
    public String getProfile() {
        return Arrays.stream(env.getActiveProfiles())
                .findFirst()
                .orElse("");
    }

}