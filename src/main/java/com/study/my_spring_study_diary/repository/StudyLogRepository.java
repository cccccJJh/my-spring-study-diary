package com.study.my_spring_study_diary.repository;

import com.study.my_spring_study_diary.controller.StudyLogController;
import com.study.my_spring_study_diary.entity.Category;
import com.study.my_spring_study_diary.entity.StudyLog;
import jakarta.annotation.PostConstruct;
import jakarta.annotation.PreDestroy;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicLong;
import java.util.stream.Collector;
import java.util.stream.Collectors;

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
     * @PostConstruct: Bean 생성 및 의존성 주입 완료 후 실행
     * 초기 데이터 설정, 리소스 초기화 등에 활용
     */

//    @PostConstruct
//    public void init() {
//        System.out.println("🚀 StudyLogRepository 초기화 완료!");
//        System.out.println("📦 데이터베이스(Map) 준비 완료!");
//
//        // 테스트용 초기 데이터 추가 (선택사항)
//        // initSampleData();
//    }

    /**
     * @PreDestroy: Bean 소멸 전 실행
     * 리소스 정리, 연결 해제 등에 활용
     */
    @PreDestroy
    public void destroy() {
        System.out.println("🔚 StudyLogRepository 종료!");
        System.out.println("🗑️ 저장된 데이터 개수: " + database.size());
    }

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

    /**
     * 학습 일지 수정 (Update)
     * Map은 같은 키로 put하면 덮어쓰므로 save와 동일하게 동작
     * 하지만 의미를 명확히 하기 위해 별도 메서드로 분리
     */
    public StudyLog update(StudyLog studyLog) {
        if (studyLog.getId() == null) {
            throw new IllegalArgumentException("수정할 학습 일지의 ID가 없습니다.");
        }
        if (!database.containsKey(studyLog.getId())) {
            throw new IllegalArgumentException(
                    "해당 학습 일지를 찾을 수 없습니다. (id: " + studyLog.getId() + ")");
        }
        database.put(studyLog.getId(), studyLog);
        return studyLog;
    }

    /**
     * ID로 존재 여부 확인
     */
    public boolean existsById(Long id) {
        return database.containsKey(id);
    }


    /**
     * 전체 학습 일지 조회 (최신순 정렬)
     */
    public List<StudyLog> findAll(){
        return database.values().stream()
                .sorted((a,b) -> b.getCreatedAt().compareTo(a.getCreatedAt()))
                .collect(Collectors.toList());
    }

    /**
     * ID로 학습 일지 조회
     * @return Optional: 값이 있을 수도, 없을 수도 있음을 명시
     */
    // Optional: null일 수 있는 값을 감싸는 컨테이너
    public Optional<StudyLog> findById(Long id) {
        return Optional.ofNullable(database.get(id));
        // database.get(id)가 null이면 Optional.empty() 반환
        // null이 아니면 Optional.of(값) 반환
    }

    /*
    * // 사용 예시
    Optional<StudyLog> result = repository.findById(1L);

    // 방법 1: isPresent()로 확인
    if (result.isPresent()) {
        StudyLog log = result.get();
    }

    // 방법 2: orElseThrow()로 없으면 예외 발생
    StudyLog log = result.orElseThrow(() ->
        new RuntimeException("학습 일지를 찾을 수 없습니다."));

    // 방법 3: orElse()로 기본값 반환
    StudyLog log = result.orElse(new StudyLog());
    *
    * */

    /**
     * 날짜로 학습 일지 조회
     */
    public List<StudyLog> findByStudyDate(LocalDate date) {
        return database.values().stream()
                .filter(log -> log.getStudyDate().equals(date))
                .sorted((a, b) -> b.getCreatedAt().compareTo(a.getCreatedAt()))
                .collect(Collectors.toList());
    }

    /**
     * 카테고리로 학습 일지 조회
     */
    public List<StudyLog> findByCategory(Category category) {
        return database.values().stream()
                .filter(log -> log.getCategory().equals(category))
                .sorted((a, b) -> b.getCreatedAt().compareTo(a.getCreatedAt()))
                .collect(Collectors.toList());
    }

    /**
     * 저장된 데이터 개수 조회
     */
    public long count() {

        return database.size();
    }

    // ========== DELETE ==========

    /**
     * ID로 학습 일지를 삭제합니다.
     *
     * @param id 삭제할 학습 일지 ID
     * @return 삭제 성공 여부 (true: 삭제됨, false: 해당 ID 없음)
     */
    public boolean deleteById(Long id) {
        // Map.remove()는 삭제된 값을 반환, 없으면 null 반환
        StudyLog removed = database.remove(id);
        return removed != null;
    }

    /**
     * ID에 해당하는 학습 일지가 존재하는지 확인합니다.
     *
     * @param id 확인할 학습 일지 ID
     * @return 존재 여부
     */
//    public boolean existsById(Long id) {
//        return database.containsKey(id);
//    }

    /**
     * 저장된 전체 학습 일지 수를 반환합니다.
     *
     * @return 학습 일지 총 개수
     */
//    public long count() {
//        return database.size();
//    }

    /**
     * 모든 학습 일지를 삭제합니다.
     * (테스트용)
     */
    public void deleteAll() {
        database.clear();
    }

    // ========== 생명주기 콜백 ==========

    @PostConstruct
    public void init() {
        System.out.println("========================================");
        System.out.println("📦 StudyLogRepository 초기화 완료!");
        System.out.println("   - 데이터 저장소(Map) 준비됨");
        System.out.println("   - ID 생성기 준비됨");
        System.out.println("========================================");
    }

    @PreDestroy
    public void cleanup() {
        System.out.println("========================================");
        System.out.println("🧹 StudyLogRepository 정리 중...");
        System.out.println("   - 저장된 데이터 수: " + database.size());
        System.out.println("   - 마지막 ID: " + (sequence.get() - 1));
        database.clear();  // 데이터 정리
        System.out.println("   - 데이터 정리 완료!");
        System.out.println("========================================");
    }

}
// @Repository 어노테이션 하나로 :
// 1. Spring Bean으로 자동 등록됨
// 2. 다른 클래스에서 주입받아 사용 가능
// 3. 데이터 접근 계층임을 명시
