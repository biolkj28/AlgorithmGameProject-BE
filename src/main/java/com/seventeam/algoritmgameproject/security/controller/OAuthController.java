package com.seventeam.algoritmgameproject.security.controller;


import com.seventeam.algoritmgameproject.domain.model.OAuthToken;
import com.seventeam.algoritmgameproject.security.dto.UserResponseDto;
import com.seventeam.algoritmgameproject.security.service.GithubLoginService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;

@Slf4j
@RestController
@RequiredArgsConstructor
public class OAuthController {
    private final GithubLoginService service;

    @GetMapping("/login/oauth2/code/github")
    public UserResponseDto githubLogin(String code) {
        OAuthToken oAuthToken = service.getOAuthToken(code);
        return service.getGithubProfile(oAuthToken);
    }

}