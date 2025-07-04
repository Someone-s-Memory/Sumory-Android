package com.sumory.model.param.diary

data class DiaryUpdateRequestParam (
    val title: String,
    val content: String,
    val feeling: String,
    val weather: String,
    val date: String,
    val pictures: List<String>,
    val change: String
)