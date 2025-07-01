package com.sumory.model.mapper.diary

import com.sumory.model.entity.calendar.CalendarDiaryListEntity
import com.sumory.model.entity.diary.DiaryListEntity
import com.sumory.model.model.diary.DateDiaryResponseModel
import java.time.LocalDate

fun DateDiaryResponseModel.toCalendarDiaryListEntity(): CalendarDiaryListEntity = CalendarDiaryListEntity(
    id = this.id,
    title = this.title,
    content = this.content,
    feeling = this.feeling,
    weather = this.weather,
    date = this.date
)
