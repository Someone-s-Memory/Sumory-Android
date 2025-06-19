package com.sumory.network.dto.auth.response

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class SignUpResponse(
    @Json(name = "success") val success: Boolean,
    @Json(name = "code") val code: Int,
    @Json(name = "msg") val msg: String,
)
