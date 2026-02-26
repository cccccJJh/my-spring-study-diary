package com.study.myspringstudydiary.auth.service;

import com.study.myspringstudydiary.auth.dao.UserDao;
import com.study.myspringstudydiary.auth.dto.LoginRequest;
import com.study.myspringstudydiary.auth.entity.User;
import com.study.myspringstudydiary.auth.exception.UsernameNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class CustomUserDetailsService {
    private final UserDao userDao;

    public Optional<User> login (LoginRequest request){
        // Get user details
        log.debug("------Login Service Start ----------------------------------");
        Optional<User> user = userDao.findByUserName(request.getUsername());
        log.debug("------Login Service End----------------------------------");
        return user;
    }


    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        User user = userDao.findByUserName(username)
                .orElseThrow(()-> new UsernameNotFoundException(
                        "해당 사용자를 찾을 수 없습니다: " + username
        ));
        return org.springframework.security.core.userdetails.User.builder()
                .username(user.getUsername())
                .password(user.getPassword())
                .build();


    }
}
