package com.sumory.data.repository.auth

import com.sumory.model.model.auth.SignInResponseModel
import com.sumory.model.param.auth.SignInRequestParam
import kotlinx.coroutines.flow.Flow

interface AuthRepository {
    suspend fun signIn(body: SignInRequestParam) : Flow<SignInResponseModel>
}