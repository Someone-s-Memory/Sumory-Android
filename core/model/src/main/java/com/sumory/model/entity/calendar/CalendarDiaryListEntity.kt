package com.sumory.model.entity.calendar

import java.time.LocalDate

data class CalendarDiaryListEntity(
    val id: Int,
    val title: String,
    val content: String,
    val emotionEmoji: String,
    val weatherEmoji: String,
    val date: LocalDate? = null
)

fun CalendarDiaryListEntity.withDate(date: LocalDate): CalendarDiaryListEntity {
    return this.copy(date = date)
}