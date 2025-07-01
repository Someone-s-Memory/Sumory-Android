package com.sumory.data.repository.diary

import com.sumory.model.model.diary.DiaryWriteResponseModel
import com.sumory.model.param.diary.DiaryWriteRequestParam
import kotlinx.coroutines.flow.Flow

interface DiaryRepository {
    suspend fun writeDiary(body: DiaryWriteRequestParam): Flow<DiaryWriteResponseModel>
}