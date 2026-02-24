package com.study.myspringstudydiary.dto.request;

import jakarta.validation.constraints.*;

import java.time.*;

/**
 * 학습 일지 수정 요청 DTO
 *
 * CREATE와 달리 모든 필드가 선택적입니다.
 * null이면 기존 값을 유지합니다.
 */
public class StudyLogUpdateRequest {

    @Size(min = 2, max=100, message ="제목은 2자 이상 100자 이하여야 합니다.")
    private String title;          // null이면 기존 값 유지

    @Size(min=10, max=5000, message="내용은 10자 이상 5000자 이하여야 합니다.")
    private String content;        // null이면 기존 값 유지

    @Pattern(regexp="^(JAVA|SPRING|JPA|DATABASE|ALGORITHM|CS|NETWORK|GIT|ETC)$", message = "카테고리는 JAVA, SPRING, JPA, DATABASE, ALGORITHM, CS, NETWORK, GIT, ETC 중 하나여야 합니다.")
    private String category;       // null이면 기존 값 유지

    @Pattern(regexp = "^(VERY_GOOD|GOOD|NORMAL|BAD|VERY_BAD)$",
            message = "이해도는 VERY_GOOD, GOOD, NORMAL, BAD, VERY_BAD 중 하나여야 합니다")
    private String understanding;  // null이면 기존 값 유지

    @Positive(message = "학습 시간은 양수여야 합니다.")
    @Max(value=1440, message="학습 시간은 1440분(24시간)을 초과할 수 없습니다.")
    private Integer studyTime;     // null이면 기존 값 유지

    @PastOrPresent(message = "학습 날짜는 현재 또는 과거여야 합니다.")
    private LocalDate studyDate;   // null이면 기존 값 유지

    // 기본 생성자
    public StudyLogUpdateRequest() {
    }

    // Getter 메서드들
    public String getTitle() { return title; }
    public String getContent() { return content; }
    public String getCategory() { return category; }
    public String getUnderstanding() { return understanding; }
    public Integer getStudyTime() { return studyTime; }
    public LocalDate getStudyDate() { return studyDate; }

    // Setter 메서드들
    public void setTitle(String title) { this.title = title; }
    public void setContent(String content) { this.content = content; }
    public void setCategory(String category) { this.category = category; }
    public void setUnderstanding(String understanding) { this.understanding = understanding; }
    public void setStudyTime(Integer studyTime) { this.studyTime = studyTime; }
    public void setStudyDate(LocalDate studyDate) { this.studyDate = studyDate; }

    /**
     * 모든 필드가 null인지 확인
     * 아무것도 수정할 내용이 없는 경우 체크용
     */
    public boolean hasNoUpdates() {
        return title == null
            && content == null
            && category == null
            && understanding == null
            && studyTime == null
            && studyDate == null;
    }
}