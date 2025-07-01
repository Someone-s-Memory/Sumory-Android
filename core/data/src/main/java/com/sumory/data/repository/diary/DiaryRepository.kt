package com.sumory.data.repository.diary

import com.sumory.model.model.diary.DiaryAllResponseModel
import com.sumory.model.model.diary.DiaryWriteResponseModel
import com.sumory.model.param.diary.DiaryWriteRequestParam
import kotlinx.coroutines.flow.Flow

interface DiaryRepository {
    suspend fun diaryWrite(body: DiaryWriteRequestParam): Flow<DiaryWriteResponseModel>
    suspend fun getDiaryAll(forceRefresh: Boolean = false): List<DiaryAllResponseModel>
}