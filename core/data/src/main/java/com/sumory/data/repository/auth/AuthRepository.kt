package com.sumory.data.repository.auth

import com.sumory.model.model.auth.SignInResponseModel
import com.sumory.model.model.auth.SignUpResponseModel
import com.sumory.model.param.auth.SignInRequestParam
import com.sumory.model.param.auth.SignUpRequestParam
import kotlinx.coroutines.flow.Flow

interface AuthRepository {
    suspend fun signIn(body: SignInRequestParam) : Flow<SignInResponseModel>
    suspend fun isSignIn(): Boolean
    suspend fun logout()
    suspend fun signUp(body: SignUpRequestParam): Flow<SignUpResponseModel>
}