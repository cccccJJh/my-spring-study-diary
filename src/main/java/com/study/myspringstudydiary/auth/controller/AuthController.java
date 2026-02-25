package com.study.myspringstudydiary.auth.controller;

import com.study.myspringstudydiary.auth.dto.LoginRequest;
import com.study.myspringstudydiary.auth.service.AuthService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@Slf4j
@RestController
@RequiredArgsConstructor  // Lombok이 생성자를 자동 생성
@RequestMapping("/api/v1/logs")
@Validated  // PathVariable, RequestParam 검증을 위해 추가
public class AuthController {

    private final AuthService authService;

    @PostMapping("/login")
    public String login(@Valid @RequestBody LoginRequest request){
        log.info("Login request for username :{}", request.getUsername());
        authService.login(request);
        return "🎉 로그인 성공! 환영합니다, " + request.getUsername() + "님!";
    }
}
