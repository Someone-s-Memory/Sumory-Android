package com.sumory.network.dto.diary.request

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class DiaryDeleteRequest(
    @Json(name = "date") val date: String,
    @Json(name = "title") val title: String,
)
