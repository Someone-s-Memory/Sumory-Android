package com.sumory.network.mapper.auth.request

import com.sumory.model.param.auth.SignUpRequestParam
import com.sumory.network.dto.auth.request.SignUpRequest

fun SignUpRequestParam.toDto(): SignUpRequest = SignUpRequest(
    userId = this.userId,
    password = this.password,
    passwordCheck = this.passwordCheck,
    nickname = this.nickname,
    email = this.email
)