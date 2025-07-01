package com.sumory.network.dto.diary.response

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class AllDiaryResponse(
    @Json(name = "id") val id: Int,
    @Json(name = "title") val title: String,
    @Json(name = "content") val content: String,
    @Json(name = "feeling") val feeling: String,
    @Json(name = "weather") val weather: String,
    @Json(name = "date") val date: String,
    @Json(name = "pictures") val pictures: List<String>,
    @Json(name = "userID") val userID: String,
)