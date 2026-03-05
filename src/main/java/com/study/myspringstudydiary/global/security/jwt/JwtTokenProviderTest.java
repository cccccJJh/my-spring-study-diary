package com.study.myspringstudydiary.global.security.jwt;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class JwtTokenProviderTest {
    private JwtTokenProvider tokenProvider;
    private final String SECRET_KEY = "test-secret-key-for-jwt-token-generation-must-be-longer-than-256-bits";

    @BeforeEach
    void setUp() {
        tokenProvider = new JwtTokenProvider(SECRET_KEY, 1800, 604800);
    }

    @Test
    @DisplayName("액세스 토큰을 생성할 수 있다")
    void createAccessToken_success() {
        // Given
        String username = "testuser";
        String role = "ROLE_USER";

        // When
        String token = tokenProvider.createAccessToken(username, role);

        // Then
        // TODO: token이 null이 아닌지 검증하세요
        // assertThat(token)...
    }

    @Test
    @DisplayName("토큰에서 username을 추출할 수 있다")
    void extractUsername_success() {
        // Given
        String username = "testuser";
        String token = tokenProvider.createAccessToken(username, "ROLE_USER");

        // When
        String extracted = tokenProvider.extractUsername(token);

        // Then
        // TODO: 추출한 username이 원본과 같은지 검증하세요
    }

    @Test
    @DisplayName("만료된 토큰은 유효하지 않다")
    void validateToken_expired() {
        // TODO: 만료 시간이 0인 TokenProvider를 생성하고 토큰 검증 테스트
    }

    @Test
    @DisplayName("잘못된 시그니처의 토큰은 유효하지 않다")
    void validateToken_invalidSignature() {
        // TODO: 다른 secret key로 생성된 토큰 검증 시 예외 발생 테스트
    }
}
