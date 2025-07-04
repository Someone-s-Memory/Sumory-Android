package com.sumory.network.mapper.diary.request

import com.sumory.model.param.diary.DiaryUpdateRequestParam
import com.sumory.network.dto.diary.request.DiaryUpdateRequest

fun DiaryUpdateRequestParam.toDto(): DiaryUpdateRequest =
    DiaryUpdateRequest(
        title = this.title,
        content = this.content,
        feeling = this.feeling,
        weather = this.weather,
        date = this.date,
        pictures = this.pictures,
        change = this.change
    )