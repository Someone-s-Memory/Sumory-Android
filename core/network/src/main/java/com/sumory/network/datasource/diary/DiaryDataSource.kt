package com.sumory.network.datasource.diary

import com.sumory.network.dto.diary.request.DiaryWriteRequest
import com.sumory.network.dto.diary.response.DiaryAllResponse
import com.sumory.network.dto.diary.response.DiaryWriteResponse
import kotlinx.coroutines.flow.Flow

interface DiaryDataSource {
    fun diaryWrite(body: DiaryWriteRequest): Flow<DiaryWriteResponse>
    fun getDiaryAll(): Flow<List<DiaryAllResponse>>
}