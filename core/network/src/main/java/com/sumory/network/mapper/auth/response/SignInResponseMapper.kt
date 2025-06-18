package com.sumory.network.mapper.auth.response

import com.sumory.model.model.auth.SignInResponseModel
import com.sumory.network.dto.auth.response.SignInResponse

fun SignInResponse.toModel(): SignInResponseModel =
    SignInResponseModel(
        success = this.success,
        code = this.code,
        msg = this.msg,
        access = this.access,
        refresh = this.refresh
    )