package com.sumory.model.param.diary

data class DiaryWriteRequestParam (
    val title: String,
    val content: String,
    val feeling: String,
    val weather: String,
    val date: String,
    val pictures: List<String>
)