package com.sumory.network.dto.diary.response

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class DiaryDeleteResponse(
    @Json(name = "message") val message: String,
)
