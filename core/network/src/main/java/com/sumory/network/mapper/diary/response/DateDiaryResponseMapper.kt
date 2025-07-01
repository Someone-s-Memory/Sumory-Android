package com.sumory.network.mapper.diary.response

import com.sumory.model.model.diary.DateDiaryResponseModel
import com.sumory.network.dto.diary.response.DateDiaryResponse

fun DateDiaryResponse.toModel(): DateDiaryResponseModel =
    DateDiaryResponseModel(
        id = this.id,
        title = this.title,
        content = this.content,
        feeling = this.feeling,
        weather = this.weather,
        date = this.date,
        pictures = this.pictures,
        userID = this.userID
    )