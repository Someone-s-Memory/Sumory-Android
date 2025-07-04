package com.sumory.network.mapper.diary.response

import com.sumory.model.model.diary.DiaryUpdateResponseModel
import com.sumory.network.dto.diary.response.DiaryUpdateResponse

fun DiaryUpdateResponse.toModel(): DiaryUpdateResponseModel =
    DiaryUpdateResponseModel(
        message = this.message
    )