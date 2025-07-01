package com.sumory.network.mapper.diary.response

import com.sumory.model.model.diary.AllDiaryResponseModel
import com.sumory.network.dto.diary.response.AllDiaryResponse

fun AllDiaryResponse.toModel(): AllDiaryResponseModel =
    AllDiaryResponseModel(
        id = this.id,
        title = this.title,
        content = this.content,
        feeling = this.feeling,
        weather = this.weather,
        date = this.date,
        pictures = this.pictures,
        userID = this.userID
    )