package com.sumory.network.datasource.auth

import com.sumory.network.dto.auth.request.SignInRequest
import com.sumory.network.dto.auth.request.SignUpRequest
import com.sumory.network.dto.auth.response.SignInResponse
import com.sumory.network.dto.auth.response.SignUpResponse
import kotlinx.coroutines.flow.Flow

interface AuthDataSource {
    fun signIn(body: SignInRequest): Flow<SignInResponse>
    fun signUp(body: SignUpRequest): Flow<SignUpResponse>
    fun signOut(): Flow<Unit>
}