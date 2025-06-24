package com.sumory.model.param.auth

data class SignUpRequestParam(
    val userId: String,
    val password: String,
    val passwordCheck: String,
    val nickname: String,
    val email: String
)