package com.sumory.network.dto.diary.request

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class DiaryWriteRequest(
    @Json(name = "title") val title: String,
    @Json(name = "content") val content: String,
    @Json(name = "feeling") val feeling: String,
    @Json(name = "weather") val weather: String,
    @Json(name = "date") val date: String,
    @Json(name = "picture") val picture: List<String>,
)
