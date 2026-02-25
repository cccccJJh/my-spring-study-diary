package com.study.myspringstudydiary.auth.service;

import com.study.myspringstudydiary.auth.dao.UserDao;
import com.study.myspringstudydiary.auth.dto.LoginRequest;
import com.study.myspringstudydiary.auth.entity.User;
import com.study.myspringstudydiary.auth.exception.AuthException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class AuthService {
    private final UserDao userDao;

    public Optional<User> login (LoginRequest request){
        // Get user details
        log.debug("------Login Service Start ----------------------------------");
        Optional<User> user = userDao.findByUserName(request.getUsername());
        log.debug("------Login Service End----------------------------------");
        return user;
    }
}
