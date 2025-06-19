package com.sumory.network.dto.auth.request

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class SignUpRequest (
    @Json(name = "userId") val userId: String,
    @Json(name = "password") val password: String,
    @Json(name = "passwordCheck") val passwordCheck: String,
    @Json(name = "nickname") val nickname: String,
)