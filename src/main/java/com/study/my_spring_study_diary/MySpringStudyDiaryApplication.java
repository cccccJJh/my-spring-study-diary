package com.study.my_spring_study_diary;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.http.converter.json.GsonBuilderUtils;

@SpringBootApplication
// = @Configuration       : 설정 클래스임을 명시
// + @EnableAutoConfiguration : 자동 설정 활성화
// + @ComponentScan       : 컴포넌트 스캔 활성화 ⭐
public class MySpringStudyDiaryApplication {

	public static void main(String[] args) {
		SpringApplication.run(MySpringStudyDiaryApplication.class, args);
	}



}
