package com.study.my_spring_study_diary;

import com.study.my_spring_study_diary.dto.request.StudyLogUpdateRequest;
import com.study.my_spring_study_diary.dto.response.StudyLogResponse;
import com.study.my_spring_study_diary.service.StudyLogService;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class MySpringStudyDiaryApplicationTests {

	@Test
	void contextLoads() {
	}

	@Autowired
	private StudyLogService studyLogService; // 실제 로직이 들어있는 서비스

	@Test
	@DisplayName("학습 로그 수정 기능 테스트")
	void updateTest() {
		// 1. 준비 (Given): 수정할 데이터 세팅
		Long targetId = 1L;
		StudyLogUpdateRequest request = new StudyLogUpdateRequest();
		request.setTitle("AOP 복습 완료");
		request.setUnderstanding("GOOD");

		// 2. 실행 (When): 수정 메서드 호출
		StudyLogResponse response = studyLogService.updateStudyLog(targetId, request);

		// 3. 검증 (Then): 값이 진짜 바뀌었는지 확인 (QA의 핵심!)
		Assertions.assertThat(response.getTitle()).isEqualTo("AOP 복습 완료");
		Assertions.assertThat(response.getUnderstanding()).isEqualTo("GOOD");

		System.out.println("수정된 제목: " + response.getTitle());
	}
}
