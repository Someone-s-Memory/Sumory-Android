package com.sumory.network.mapper.diary.response

import com.sumory.model.model.diary.DiaryDetailResponseModel
import com.sumory.network.dto.diary.response.DiaryDetailResponse

fun DiaryDetailResponse.toModel(): DiaryDetailResponseModel =
    DiaryDetailResponseModel(
        id = this.id,
        title = this.title,
        content = this.content,
        feeling = this.feeling,
        weather = this.weather,
        date = this.date,
        pictures = this.pictures,
        userID = this.userID
    )