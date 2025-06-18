package com.sumory.model.entity.diary

data class DiaryListEntity(
    val id: Int,
    val title: String,
    val date: String,
    val emotionEmoji: String,
    val weatherEmoji: String
)