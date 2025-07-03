package com.sumory.network.mapper.diary.response

import com.sumory.model.model.diary.DiaryDeleteResponseModel
import com.sumory.network.dto.diary.response.DiaryDeleteResponse

fun DiaryDeleteResponse.toModel(): DiaryDeleteResponseModel =
    DiaryDeleteResponseModel(
        message = this.message
    )