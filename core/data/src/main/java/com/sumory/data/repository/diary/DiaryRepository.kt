package com.sumory.data.repository.diary

import com.sumory.model.model.diary.AllDiaryResponseModel
import com.sumory.model.model.diary.DateDiaryResponseModel
import com.sumory.model.model.diary.DiaryDeleteResponseModel
import com.sumory.model.model.diary.DiaryDetailResponseModel
import com.sumory.model.model.diary.DiaryWriteResponseModel
import com.sumory.model.param.diary.DiaryWriteRequestParam
import kotlinx.coroutines.flow.Flow

interface DiaryRepository {
    suspend fun diaryWrite(body: DiaryWriteRequestParam): Flow<DiaryWriteResponseModel>
    suspend fun getDateDiary(date: String, forceRefresh: Boolean = false): Flow<List<DateDiaryResponseModel>>
    suspend fun getAllDiary(forceRefresh: Boolean = false): Flow<List<AllDiaryResponseModel>>
    suspend fun getDiaryDetail(diaryId: Int): DiaryDetailResponseModel
    suspend fun deleteDiary(date: String, title: String): Flow<DiaryDeleteResponseModel>
    suspend fun clearAllCache()
}