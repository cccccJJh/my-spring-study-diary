package com.study.myspringstudydiary.dto.response;

import com.study.myspringstudydiary.entity.StudyLog;
import lombok.Builder;
import lombok.Value;

import java.time.LocalDate;
import java.time.LocalDateTime;
/**
 * Study Log Response DTO with Lombok
 * Using @Value for immutable response
 *
 * Before: 54 lines
 * After: 35 lines (35% reduction)
 */
@Value
@Builder
public class StudyLogResponse {

    Long id;
    String title;
    String content;
    String category;
    String categoryIcon;
    String understanding;
    String understandingEmoji;
    Integer studyTime;
    LocalDate studyDate;
    LocalDateTime createdAt;
    LocalDateTime updatedAt;

    /**
     * Convert Entity to Response DTO
     * Using static factory method with Builder pattern
     */
    public static StudyLogResponse from(StudyLog studyLog) {
        return StudyLogResponse.builder()
                .id(studyLog.getId())
                .title(studyLog.getTitle())
                .content(studyLog.getContent())
                .category(studyLog.getCategory().name())
                .categoryIcon(studyLog.getCategory().getIcon())
                .understanding(studyLog.getUnderstanding().name())
                .understandingEmoji(studyLog.getUnderstanding().getEmoji())
                .studyTime(studyLog.getStudyTime())
                .studyDate(studyLog.getStudyDate())
                .createdAt(studyLog.getCreatedAt())
                .updatedAt(studyLog.getUpdatedAt())
                .build();
    }

    // Getter 메서드들
    public Long getId() { return id; }
    public String getTitle() { return title; }
    public String getContent() { return content; }
    public String getCategory() { return category; }
    public String getCategoryIcon() { return categoryIcon; }
    public String getUnderstanding() { return understanding; }
    public String getUnderstandingEmoji() { return understandingEmoji; }
    public Integer getStudyTime() { return studyTime; }
    public LocalDate getStudyDate() { return studyDate; }
    public LocalDateTime getCreatedAt() { return createdAt; }
    public LocalDateTime getUpdatedAt() { return updatedAt; }
}