package com.sumory.network.dto.auth.request

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class SignInRequest(
    @Json(name = "userId") val userId: String,
    @Json(name = "password") val password: String,
)
