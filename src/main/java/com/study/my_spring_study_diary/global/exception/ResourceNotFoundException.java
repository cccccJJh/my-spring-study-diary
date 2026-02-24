package com.study.my_spring_study_diary.global.exception;

public class ResourceNotFoundException extends RuntimeException{

    public ResourceNotFoundException(String message){
        super(message);
    }
    
    public ResourceNotFoundException(String message, Throwable cause){
        super(message, cause);
    }
}
