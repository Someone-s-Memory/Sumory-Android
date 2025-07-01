package com.sumory.network.datasource.diary

import com.sumory.datastore.auth.TokenDataStore
import com.sumory.network.api.DiaryApi
import com.sumory.network.dto.diary.request.DiaryWriteRequest
import com.sumory.network.dto.diary.response.DiaryAllResponse
import com.sumory.network.dto.diary.response.DiaryWriteResponse
import com.sumory.network.util.performApiRequest
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.firstOrNull
import javax.inject.Inject

class DiaryDataSourceImpl @Inject constructor(
    private val api: DiaryApi,
    private val tokenDataStore: TokenDataStore
): DiaryDataSource {
    override fun diaryWrite (body: DiaryWriteRequest): Flow<DiaryWriteResponse> =
        performApiRequest { api.diaryWrite(body = body) }

    override fun getDiaryAll(): Flow<List<DiaryAllResponse>> =
        performApiRequest {
            val token = tokenDataStore.accessToken.firstOrNull() ?: ""
            api.getDiaryAll(
                token = "Bearer $token"
            )
        }
}