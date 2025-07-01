package com.sumory.network.mapper.diary.response

import com.sumory.model.model.diary.DiaryWriteResponseModel
import com.sumory.network.dto.diary.response.DiaryWriteResponse

fun DiaryWriteResponse.toModel(): DiaryWriteResponseModel =
    DiaryWriteResponseModel(
        message = this.message
    )