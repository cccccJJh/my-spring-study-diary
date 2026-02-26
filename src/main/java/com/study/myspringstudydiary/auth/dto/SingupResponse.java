package com.study.myspringstudydiary.auth.dto;

import com.study.myspringstudydiary.study_log.dto.response.StudyLogDeleteResponse;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SingupResponse {
    private Long id;
    private String username;
    private String password;
    private String email;



    /**
     * Static factory method
     */
    public static SingupResponse of(Long id, String username, String email) {
        return SingupResponse.builder()
                .id(id)
                .username(username)
                .email(email)
                .build();
    }
}
