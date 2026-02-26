package com.study.myspringstudydiary.auth.controller;

import com.study.myspringstudydiary.auth.dto.LoginRequest;
import com.study.myspringstudydiary.auth.dto.SignupRequest;
import com.study.myspringstudydiary.auth.dto.SingupResponse;
import com.study.myspringstudydiary.auth.service.AuthService;
import com.study.myspringstudydiary.auth.service.CustomUserDetailsService;
import com.study.myspringstudydiary.study_log.dto.response.ApiResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
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

    private final CustomUserDetailsService customUserDetailsService;
    private final AuthService authService;

    @PostMapping("/login")
    public String login(@Valid @RequestBody LoginRequest request){
        log.info("Login request for username :{}", request.getUsername());
        customUserDetailsService.login(request);
        return "🎉 로그인 성공! 환영합니다, " + request.getUsername() + "님!";
    }


    /**
     * 회원가입
     * POST /api/auth/signup
     *
     * 로그인(POST /api/auth/login)은 JsonUsernamePasswordAuthenticationFilter가 처리하므로
     * Controller에 login 엔드포인트가 없습니다.
     */
    @PostMapping("/signup")
    public ResponseEntity<ApiResponse<SingupResponse>> signup(
            @Valid @RequestBody SignupRequest request){
        log.info("Signup request for username: {} ", request.getUsername());
        SingupResponse response = authService.signup(request);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(ApiResponse.success(response));

    }



}
