package com.study.my_spring_study_diary.global.exception;

import com.study.my_spring_study_diary.global.common.ApiResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;


// GlobalExceptionHandler가 예외를 처리
@RestControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(StudyLogNotFoundException.class)
    public ResponseEntity<ApiResponse<Void>> handleNotFound(StudyLogNotFoundException e) {
        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .body(ApiResponse.error("STUDY_LOG_NOT_FOUND", e.getMessage()));
    }
}



