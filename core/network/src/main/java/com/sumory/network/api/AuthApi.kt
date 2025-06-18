package com.sumory.network.api

import com.sumory.network.dto.auth.request.SignInRequest
import com.sumory.network.dto.auth.response.SignInResponse
import retrofit2.http.Body
import retrofit2.http.POST

interface AuthApi {
    @POST("sign-in")
    suspend fun signIn(
        @Body request: SignInRequest
    ): SignInResponse
}