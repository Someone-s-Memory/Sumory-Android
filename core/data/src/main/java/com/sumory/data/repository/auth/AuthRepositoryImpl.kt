package com.sumory.data.repository.auth

import com.sumory.data.repository.diary.DiaryRepository
import com.sumory.datastore.auth.TokenDataStore
import com.sumory.model.model.auth.SignInResponseModel
import com.sumory.model.model.auth.SignUpResponseModel
import com.sumory.model.param.auth.SignInRequestParam
import com.sumory.model.param.auth.SignUpRequestParam
import com.sumory.network.datasource.auth.AuthDataSource
import com.sumory.network.mapper.auth.request.toDto
import com.sumory.network.mapper.auth.response.toModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.flow.onCompletion
import kotlinx.coroutines.flow.transform
import javax.inject.Inject

class AuthRepositoryImpl @Inject constructor(
    private val authDataSource: AuthDataSource,
    private val tokenDataStore: TokenDataStore,
    private val diaryRepository: DiaryRepository
) : AuthRepository {

    override suspend fun signIn(body: SignInRequestParam): Flow<SignInResponseModel> {
        return authDataSource.signIn(
            body = body.toDto()
        ).transform { response ->
            // 토큰 저장
            tokenDataStore.saveTokens(response.access, response.refresh)

            // 모델 변환 후 emit
            emit(response.toModel())
        }
    }

    override suspend fun isSignIn(): Boolean {
        val access = tokenDataStore.accessToken.firstOrNull()
        val refresh = tokenDataStore.refreshToken.firstOrNull()
        return !access.isNullOrEmpty() && !refresh.isNullOrEmpty()
    }

    override suspend fun signUp(body: SignUpRequestParam): Flow<SignUpResponseModel> {
        return authDataSource.signUp(body.toDto()).transform {
            emit(it.toModel())
        }
    }

    override suspend fun signOut(): Flow<Unit> {
        return authDataSource.signOut().onCompletion {
            tokenDataStore.clearTokens()
            diaryRepository.clearAllCache()
        }
    }
}
