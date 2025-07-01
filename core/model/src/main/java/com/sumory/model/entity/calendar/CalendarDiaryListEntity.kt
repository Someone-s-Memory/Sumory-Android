package com.sumory.model.entity.calendar

data class CalendarDiaryListEntity(
    val id: Int,
    val title: String,
    val content: String,
    val feeling: String,
    val weather: String,
    val date: String
)