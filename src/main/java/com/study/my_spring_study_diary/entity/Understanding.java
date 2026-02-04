package com.study.my_spring_study_diary.entity;

public enum Understanding {

    //# 이해도 Enum
    //# StudyLog 엔티티
    VERY_GOOD("😎", "완벽히 이해했어요"),
    GOOD("😊", "잘 이해했어요"),
    NORMAL("😐", "보통이에요"),
    BAD("😥", "어려웠어요"),
    VERY_BAD("😵", "이해 못했어요");

    private final String emoji;
    private final String description;

    Understanding(String emoji, String description){
        this.emoji = emoji;
        this.description = description;
    }

    public String getDescription(){
        return description;
    }

    public String getEmoji(){
        return emoji;
    }

}
