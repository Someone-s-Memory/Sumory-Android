package com.sumory.network.datasource.diary

import com.sumory.network.api.DiaryApi
import com.sumory.network.dto.diary.request.DiaryWriteRequest
import com.sumory.network.dto.diary.response.DiaryAllResponse
import com.sumory.network.dto.diary.response.DiaryWriteResponse
import com.sumory.network.util.performApiRequest
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class DiaryDataSourceImpl @Inject constructor(
    private val api: DiaryApi
): DiaryDataSource {
    override fun diaryWrite (body: DiaryWriteRequest): Flow<DiaryWriteResponse> =
        performApiRequest { api.diaryWrite(body = body) }

    override fun getDiaryAll(): Flow<List<DiaryAllResponse>> =
        performApiRequest { api.getDiaryAll() }
}