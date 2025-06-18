package com.sumory.network.mapper.auth.request

import com.sumory.model.param.auth.SignInRequestParam
import com.sumory.network.dto.auth.request.SignInRequest

fun SignInRequestParam.toDto(): SignInRequest =
    SignInRequest(
        userId = this.userId,
        password = this.password
    )