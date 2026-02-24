package com.study.myspringstudydiary.dto.request;

import com.study.myspringstudydiary.common.ValidationGroups;
import com.study.myspringstudydiary.entity.Category;
import com.study.myspringstudydiary.entity.EnumValid;
import com.study.myspringstudydiary.entity.Understanding;
import jakarta.validation.constraints.*;
import lombok.Getter;

import java.time.LocalDate;

@Getter
public class StudyLogRequest {

    @NotBlank(groups = {ValidationGroups.Create.class, ValidationGroups.Update.class})
    @Size(max=100)
    private String title;

    @NotBlank(groups = ValidationGroups.Create.class) // 생성 시만 필수
    @Size(max=1000)
    private String content;

    @NotNull(groups = ValidationGroups.Create.class)   // 생성 시만 필수
    @Min(value = 1)
    private Integer studyTime;

    @EnumValid(enumClass = Category.class,
            groups = ValidationGroups.Create.class,  // 생성 시만 검증
            allowNull = true)  // Draft에서는 null 허용
    private String category;

    @EnumValid(enumClass = Understanding.class,
            groups = ValidationGroups.Create.class,  // 생성 시만 검증
            allowNull = true)  // Draft에서는 null 허용
    private String understanding;

    @PastOrPresent(message = "학습 날짜는 현재 또는 과거여야 합니다.")
    private LocalDate studyDate;   // null이면 기존 값 유지
}
