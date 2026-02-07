package com.study.my_spring_study_diary.service;


import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Component
@Scope("prototype")
public class PrototypeBean {
    public PrototypeBean(){
        System.out.println("나 출력되며객체 새로 생성 된 거임  " + this);
    }
}
