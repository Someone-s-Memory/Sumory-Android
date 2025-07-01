package com.sumory.network.datasource.diary

import com.sumory.network.dto.diary.request.DateDiaryRequest
import com.sumory.network.dto.diary.request.DiaryWriteRequest
import com.sumory.network.dto.diary.response.AllDiaryResponse
import com.sumory.network.dto.diary.response.DateDiaryResponse
import com.sumory.network.dto.diary.response.DiaryWriteResponse
import kotlinx.coroutines.flow.Flow

interface DiaryDataSource {
    fun diaryWrite(body: DiaryWriteRequest): Flow<DiaryWriteResponse>
    fun getDateDiary(body: DateDiaryRequest): Flow<List<DateDiaryResponse>>
    fun getAllDiary(): Flow<List<AllDiaryResponse>>
}