package com.sumory.network.datasource.diary

import com.sumory.datastore.auth.TokenDataStore
import com.sumory.network.api.DiaryApi
import com.sumory.network.dto.diary.request.DiaryWriteRequest
import com.sumory.network.dto.diary.response.AllDiaryResponse
import com.sumory.network.dto.diary.response.DateDiaryResponse
import com.sumory.network.dto.diary.response.DiaryDetailResponse
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
        performApiRequest {
            val token = tokenDataStore.accessToken.firstOrNull() ?: ""
            api.diaryWrite(
                token = "Bearer $token",
                body = body
            )
        }

    override fun getDateDiary(date: String): Flow<List<DateDiaryResponse>> =
        performApiRequest {
            val token = tokenDataStore.accessToken.firstOrNull() ?: ""
            api.getDateDiary(
                token = "Bearer $token",
                date = date
            )
        }

    override fun getAllDiary(): Flow<List<AllDiaryResponse>> =
        performApiRequest {
            val token = tokenDataStore.accessToken.firstOrNull() ?: ""
            api.getAllDiary(
                token = "Bearer $token"
            )
        }

    override fun getDiaryDetail(diaryId: Int): Flow<List<DiaryDetailResponse>> =
        performApiRequest {
            val token = tokenDataStore.accessToken.firstOrNull() ?: ""
            api.getDiaryDetail(
                token = "Bearer $token",
                diaryId = diaryId
            )
        }

}