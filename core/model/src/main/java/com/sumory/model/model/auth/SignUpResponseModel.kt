package com.sumory.model.model.auth

data class SignUpResponseModel(
    val success: Boolean,
    val code: Int,
    val msg: String
)