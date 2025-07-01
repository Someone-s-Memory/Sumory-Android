package com.sumory.network.mapper.diary.response

import com.sumory.model.model.diary.DiaryAllResponseModel
import com.sumory.network.dto.diary.response.DiaryAllResponse

fun DiaryAllResponse.toModel(): DiaryAllResponseModel =
    DiaryAllResponseModel(
        id = this.id,
        title = this.title,
        content = this.content,
        feeling = this.feeling,
        weather = this.weather,
        date = this.date,
        pictures = this.pictures,
        userID = this.userID
    )