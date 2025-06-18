package com.sumory.network.datasource.auth

import com.sumory.network.dto.auth.request.SignInRequest
import com.sumory.network.dto.auth.response.SignInResponse
import kotlinx.coroutines.flow.Flow

interface AuthDataSource {
    fun signIn(body: SignInRequest): Flow<SignInResponse>
}