package com.study.myspringstudydiary.entity;


import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;

@Target({ElementType.FIELD, ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = EnumValidator.class)
@Documented
public @interface EnumValid {

    String message() default "유효하지 않은 값입니다";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

    /*
    Enum 클래스 타입
     */
    Class<? extends Enum<?>> enumClass();

    /*
    null 허용 할지 여부 (기본값 : false)
     */
    boolean allowNull() default false;
    /*
    대소문자를 무시할지 여부 (기본값 : false)
     */
    boolean ignoreCase() default false;
}
