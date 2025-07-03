package com.sumory.network.mapper.diary.request

import com.sumory.model.param.diary.DiaryDeleteRequestParam
import com.sumory.network.dto.diary.request.DiaryDeleteRequest

fun DiaryDeleteRequestParam.toDto(): DiaryDeleteRequest =
    DiaryDeleteRequest(
        date = this.date,
        title = this.title,
    )