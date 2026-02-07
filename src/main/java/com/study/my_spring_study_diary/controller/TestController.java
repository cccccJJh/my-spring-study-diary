package com.study.my_spring_study_diary.controller;

import com.study.my_spring_study_diary.entity.StudyLog;
import com.study.my_spring_study_diary.service.PrototypeBean;
import com.study.my_spring_study_diary.service.SingletonBean;
import com.study.my_spring_study_diary.service.StudyLogService;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/test")
public class TestController {
    private final StudyLogService service1;
    private final StudyLogService service2;

    public TestController(StudyLogService service1, StudyLogService service2){
        this.service1 = service1;
        this.service2 = service2;
    }


    @GetMapping("/singleton")
    public String checkSingleton(){
        // 두 참조가 같은 인스턴스를 가리키는지 확인
        boolean isSame = (service1 == service2);

        return "service1 == service2 ? " + isSame;
    }


    @Autowired private SingletonBean singleton1;
    @Autowired private SingletonBean singleton2;

    @Autowired private PrototypeBean prototype1;
    @Autowired private PrototypeBean prototype2;

    @GetMapping("/scope-test")
    public String test() {
        // Singleton: 같은 인스턴스
        System.out.println(singleton1 == singleton2);  // true

        // Prototype: 다른 인스턴스
        System.out.println(prototype1 == prototype2);  // false

        return "Check console!";
    }


    @Autowired
    private ObjectProvider<PrototypeBean> prototypeBeanProvider; // 컨테이너에 요청할 수 있는 대리자

    @GetMapping("/real-prototype")
    public String realPrototype() {
        // getObject()를 호출할 때마다 새로운 PrototypeBean이 생성됩니다!
        PrototypeBean p1 = prototypeBeanProvider.getObject();
        PrototypeBean p2 = prototypeBeanProvider.getObject();

        System.out.println("p1: " + p1);
        System.out.println("p2: " + p2);
        System.out.println("p1 == p2 ? " + (p1 == p2));

        return "로그를 보세요! 새로고침 할 때마다 '객체 새로 생성 된 거임'이 계속 뜰 걸요?";
    }
}
