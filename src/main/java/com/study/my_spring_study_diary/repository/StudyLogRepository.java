package com.study.my_spring_study_diary.repository;

import com.study.my_spring_study_diary.entity.StudyLog;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicLong;

/**
 * 학습 일지 저장소
 *
 * @Repository 어노테이션 설명:
 * - 이 클래스를 Spring Bean으로 등록합니다
 * - 데이터 접근 계층임을 명시합니다
 * - 데이터 접근 관련 예외를 Spring의 DataAccessException으로 변환해줍니다
 *
 * 실제 프로젝트에서는 JPA, MyBatis 등을 사용하지만,
 * 이번 강의에서는 Map을 사용하여 데이터를 저장합니다.
 */

@Repository  // ⭐ Spring Bean으로 등록!
public class StudyLogRepository {


    //# 저장소 (Map 기반)

    //데이터 저장소 (실제 DB 대신 MAP 사용)
    private final Map<Long, StudyLog> database = new HashMap<>();

    //ID 자동 증가를 위한 시퀀스
    private final AtomicLong sequence = new AtomicLong(1);

    /**
     * 학습 일지 저장
     * @param studyLog 저장할 학습 일지
     * @return 저장된 학습 일지 (ID 포함)
     */
    public StudyLog save(StudyLog studyLog) {
        // ID가 없으면 새로운 ID 부여
        if (studyLog.getId() == null) {
            studyLog.setId(sequence.getAndIncrement());
        }

        // Map에 저장
        database.put(studyLog.getId(), studyLog);

        return studyLog;
    }
}
// @Repository 어노테이션 하나로 :
// 1. Spring Bean으로 자동 등록됨
// 2. 다른 클래스에서 주입받아 사용 가능
// 3. 데이터 접근 계층임을 명시
