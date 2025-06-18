package com.sumory.data.repository.auth

import com.sumory.model.model.auth.SignInResponseModel
import com.sumory.model.param.auth.SignInRequestParam
import com.sumory.network.datasource.auth.AuthDataSource
import com.sumory.network.mapper.auth.request.toDto
import com.sumory.network.mapper.auth.response.toModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.transform
import javax.inject.Inject

class AuthRepositoryImpl @Inject constructor(
    private val authDataSource: AuthDataSource,
): AuthRepository{

    override suspend fun signIn(body: SignInRequestParam): Flow<SignInResponseModel> {
        return authDataSource.signIn(
            body = body.toDto()
        ).transform { response ->
            emit(response.toModel())
        }
    }
}