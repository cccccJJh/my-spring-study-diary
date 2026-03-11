package com.study.myspringstudydiary.auth.service;

import com.study.myspringstudydiary.auth.dto.KakaoTokenResponse;
import com.study.myspringstudydiary.auth.dto.KakaoUserResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestClient;
// ===== 카카오 API 서비스
@Service
@Slf4j
public class KakaoApiService {

    private final RestClient kakaoAuthClient;
    private final RestClient kakaoApiClient;
    private final String clientId;
    private final String clientSecret;
    private final String redirectUri;


    public KakaoApiService(
            @Qualifier("kakaoAuthClient") RestClient kakaoAuthClient,
            @Qualifier("kakaoApiClient") RestClient kakaoApiClient,
            @Value("${kakao.client-id}") String clientId,
            @Value("${kakao.client-secret}") String clientSecret,
            @Value("${kakao.redirect-uri}") String redirectUri)
    {
        this.kakaoAuthClient = kakaoAuthClient;
        this.kakaoApiClient = kakaoApiClient;
        this.clientId = clientId;
        this.clientSecret = clientSecret;
        this.redirectUri = redirectUri;
    }

    /**
     * 1단계: 인가 코드로 액세스 토큰 요청
     */
    public KakaoTokenResponse getAccessToken(String authorizationCode) {
        log.info("카카오 토큰 요청 시작");

        MultiValueMap<String, String> formData = new LinkedMultiValueMap<>();
        formData.add("grant_type", "authorization_code");
        formData.add("client_id", clientId);
        formData.add("client_secret", clientSecret);
        formData.add("redirect_uri", redirectUri);
        formData.add("code", authorizationCode);

        try {
            KakaoTokenResponse response = kakaoAuthClient.post()
                    .uri("/oauth/token")
                    .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                    .body(formData)
                    .retrieve()
                    .body(KakaoTokenResponse.class);

            log.info("카카오 토큰 요청 성공");
            return response;

        } catch (HttpClientErrorException e) {
            log.error("카카오 토큰 요청 실패: {}", e.getResponseBodyAsString());
            throw new KakaoApiException("카카오 인증 실패", e);
        }
    }

    /**
     * 2단계: 액세스 토큰으로 사용자 정보 조회
     */
    public KakaoUserResponse getUserInfo(String accessToken) {
        log.info("카카오 사용자 정보 조회 시작");

        try {
            KakaoUserResponse response = kakaoApiClient.get()
                    .uri("/v2/user/me")
                    .header("Authorization", "Bearer " + accessToken)
                    .retrieve()
                    .body(KakaoUserResponse.class);

            log.info("카카오 사용자 정보 조회 성공 - 카카오ID: {}", response.getId());
            return response;

        } catch (HttpClientErrorException e) {
            log.error("카카오 사용자 정보 조회 실패: {}", e.getResponseBodyAsString());
            throw new KakaoApiException("사용자 정보 조회 실패", e);
        }
    }

}
