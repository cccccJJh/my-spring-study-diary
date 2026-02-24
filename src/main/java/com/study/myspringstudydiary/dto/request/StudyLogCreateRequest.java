package com.study.myspringstudydiary.dto.request;

import com.study.myspringstudydiary.entity.Category;
import com.study.myspringstudydiary.entity.EnumValid;
import com.study.myspringstudydiary.entity.Understanding;
import jakarta.validation.constraints.*;

import java.time.LocalDate;

public class StudyLogCreateRequest {

    @NotBlank(message="제목은 필수입니다.")
    @Size(min = 2, max = 100, message = "제목은 2자 이상 100자 이하여야 합니다.")
    private String title;

    @NotBlank(message="내용은 필수입니다.")
    @Size(min=10, max=5000, message="내용은 10자 이상 5000자 이하여야 합니다.")
    private String content;

    @NotBlank(message="카테고리는 필수입니다.")
    //@Pattern(regexp="^(JAVA|SPRING|JPA|DATABASE|ALGORITHM|CS|NETWORK|GIT|ETC)$", message = "카테고리는 JAVA, SPRING, JPA, DATABASE, ALGORITHM, CS, NETWORK, GIT, ETC 중 하나여야 합니다")
    @EnumValid(enumClass = Category.class, message="유효하지 않은 카테고리입니다.")
    private String category;

    @NotBlank(message = "이해도는 필수입니다")
    //@Pattern(regexp = "^(VERY_GOOD|GOOD|NORMAL|BAD|VERY_BAD)$",
    //        message = "이해도는 VERY_GOOD, GOOD, NORMAL, BAD, VERY_BAD 중 하나여야 합니다")
    @EnumValid(enumClass = Understanding.class, message="유효하지 않은 이해도입니다.")
    private String understanding;

    @NotNull(message = "학습 시간은 필수입니다")
    @Positive(message = "학습 시간은 양수여야 합니다")
    @Max(value = 1440, message = "학습 시간은 1440분(24시간)을 초과할 수 없습니다")
    private Integer studyTime;

    @PastOrPresent(message = "학습 날짜는 현재 또는 과거여야 합니다")
    private LocalDate studyDate;

    // 기본 생성자 (JSON 역직렬화를 위해 필요)
    public StudyLogCreateRequest() {
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
}