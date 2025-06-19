package com.sumory.network.api

import com.sumory.network.dto.auth.request.SignInRequest
import com.sumory.network.dto.auth.request.SignUpRequest
import com.sumory.network.dto.auth.response.SignInResponse
import com.sumory.network.dto.auth.response.SignUpResponse
import retrofit2.http.Body
import retrofit2.http.POST

interface AuthApi {
    @POST("sign-in")
    suspend fun signIn(
        @Body body: SignInRequest
    ): SignInResponse

    @POST("sign-up")
    suspend fun signUp(
        @Body body: SignUpRequest
    ): SignUpResponse
}