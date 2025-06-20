package com.sumory.model.entity.diary

data class DiaryDetailEntity(
    val id: Int,
    val title: String,
    val content: String,
    val createdDate: String,
    val feeling: String,
    val weather: String,
    val characterCount: Int
)