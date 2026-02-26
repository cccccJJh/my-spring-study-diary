package com.study.myspringstudydiary.auth.service;

import com.study.myspringstudydiary.auth.dao.UserDao;
import com.study.myspringstudydiary.auth.dto.SignupRequest;
import com.study.myspringstudydiary.auth.dto.SingupResponse;
import com.study.myspringstudydiary.auth.entity.User;
import com.study.myspringstudydiary.auth.entity.UserRole;
import com.study.myspringstudydiary.study_log.exception.DuplicateResourceException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Slf4j
@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserDao userDao;
    private final PasswordEncoder passwordEncoder;

    @Transactional
    public SingupResponse signup(SignupRequest request){
        log.info("Signup attempt for username : {}, email: {}", request.getUsername(), request.getEmail());

        //아이디 중복 검사
        if(userDao.existsByUsername(request.getUsername())){
            throw new DuplicateResourceException(
                    "Username alread exists: " + request.getUsername()
            );
        }

        //이메일 중복 검사
        if(userDao.existsByEmail(request.getEmail())){
            throw new DuplicateResourceException(
                    "Email alread exists: " + request.getEmail()
            );
        }

        //비밀번호암호화 후 저장
        User newUser = User.builder()
                .username(request.getUsername())
                .email(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword()))
                .role(UserRole.USER)
                .enabled(true)
                .build();

        User savedUser = userDao.save(newUser);
        log.info("User registered successfully: {} " , savedUser.getUsername());

        return SingupResponse.of(
                savedUser.getId(),
                savedUser.getUsername(),
                savedUser.getEmail()
        );

    }
}
