package com.sumory.network.datasource.auth

import com.sumory.network.api.AuthApi
import com.sumory.network.dto.auth.request.SignInRequest
import com.sumory.network.dto.auth.request.SignUpRequest
import com.sumory.network.dto.auth.response.SignInResponse
import com.sumory.network.dto.auth.response.SignUpResponse
import com.sumory.network.util.performApiRequest
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class AuthDataSourceImpl @Inject constructor(
    private val api: AuthApi
): AuthDataSource{
    override fun signIn(body: SignInRequest): Flow<SignInResponse> =
        performApiRequest { api.signIn(body = body) }

    override fun signUp(body: SignUpRequest): Flow<SignUpResponse> =
        performApiRequest { api.signUp(body) }

    override fun signOut(): Flow<Unit> =
        performApiRequest { api.signOut() }
}