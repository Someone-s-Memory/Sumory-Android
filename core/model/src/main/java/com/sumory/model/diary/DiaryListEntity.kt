package com.sumory.model.diary

data class DiaryListEntity(
    val id: Int,
    val title: String,
    val date: String,
    val emotionEmoji: String,
    val weatherEmoji: String
)