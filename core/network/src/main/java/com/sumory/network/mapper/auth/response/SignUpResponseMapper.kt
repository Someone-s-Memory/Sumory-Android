package com.sumory.network.mapper.auth.response

import com.sumory.model.model.auth.SignUpResponseModel
import com.sumory.network.dto.auth.response.SignUpResponse

fun SignUpResponse.toModel(): SignUpResponseModel = SignUpResponseModel(
    success = success,
    code = this.code,
    msg = this.msg
)