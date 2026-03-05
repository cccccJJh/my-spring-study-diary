package com.study.myspringstudydiary.auth.dto;

import com.study.myspringstudydiary.auth.entity.UserRole;
import lombok.*;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class LoginResponse {

    private String accessToKen;
    private String username;
    private String password;
    private String userRole;


    /**
     * Static factory method
     */
    public static LoginResponse of(String accessToKen, String username, String password, String userRole) {
        return LoginResponse.builder()
                .accessToKen(accessToKen)
                .username(username)
                .password(password)
                .userRole(userRole)
                .build();
    }
}
