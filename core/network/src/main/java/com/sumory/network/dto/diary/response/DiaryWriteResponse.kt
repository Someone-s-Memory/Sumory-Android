package com.sumory.network.dto.diary.response

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class DiaryWriteResponse(
    @Json(name = "message") val message: String,
)
