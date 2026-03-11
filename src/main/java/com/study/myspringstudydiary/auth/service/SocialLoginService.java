package com.study.myspringstudydiary.auth.service;

import com.study.myspringstudydiary.auth.dao.UserDao;
import com.study.myspringstudydiary.auth.dto.KakaoTokenResponse;
import com.study.myspringstudydiary.auth.dto.KakaoUserResponse;
import com.study.myspringstudydiary.auth.dto.LoginResponse;
import com.study.myspringstudydiary.auth.entity.User;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

// ===== 소셜 로그인 서비스 =====
@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class SocialLoginService {

    private final KakaoApiService kakaoApiService;
    private final UserDao userRepository;
    private final JwtTokenProvider jwtTokenProvider;

    /**
     * 카카오 로그인 전체 플로우
     *
     * 1. 카카오에서 받은 인가 코드로 액세스 토큰 요청
     * 2. 액세스 토큰으로 사용자 정보 조회
     * 3. 기존 회원이면 로그인, 신규 회원이면 가입 처리
     * 4. JWT 토큰 발급
     */
    public LoginResponse kakaoLogin(String authorizationCode) {
        // 1. 카카오 액세스 토큰 획득
        KakaoTokenResponse tokenResponse =
                kakaoApiService.getAccessToken(authorizationCode);

        // 2. 카카오 사용자 정보 조회
        KakaoUserResponse kakaoUser =
                kakaoApiService.getUserInfo(tokenResponse.getAccessToken());

        // 3. 회원 조회 또는 생성
        User user = userRepository
                .findByProviderAndProviderId("KAKAO", String.valueOf(kakaoUser.getId()))
                .orElseGet(() -> createUser(kakaoUser));

        // 4. JWT 토큰 발급
        String jwtToken = jwtTokenProvider.createToken(user.getId());

        return new LoginResponse(jwtToken, UserResponse.from(user));
    }

    private User createUser(KakaoUserResponse kakaoUser) {
        KakaoUserResponse.KakaoAccount account = kakaoUser.getKakaoAccount();

        User newUser = User.builder()
                .email(account.getEmail())
                .nickname(account.getProfile().getNickname())
                .profileImage(account.getProfile().getProfileImageUrl())
                .provider("KAKAO")
                .providerId(String.valueOf(kakaoUser.getId()))
                .build();

        return userRepository.save(newUser);
    }
}
