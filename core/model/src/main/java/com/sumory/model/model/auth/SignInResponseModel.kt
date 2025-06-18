package com.sumory.model.model.auth

data class SignInResponseModel(
    val success: Boolean,
    val code: Int,
    val msg: String,
    val access: String,
    val refresh: String
)