package com.sumory.network.mapper.diary.request

import com.sumory.model.param.diary.DiaryWriteRequestParam
import com.sumory.network.dto.diary.request.DiaryWriteRequest

fun DiaryWriteRequestParam.toDto(): DiaryWriteRequest =
    DiaryWriteRequest(
        title = this.title,
        content = this.content,
        feeling = this.feeling,
        weather = this.weather,
        date = this.date,
        pictures = this.pictures
    )