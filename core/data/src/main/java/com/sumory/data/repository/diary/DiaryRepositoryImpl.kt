package com.sumory.data.repository.diary

import com.sumory.model.model.diary.AllDiaryResponseModel
import com.sumory.model.model.diary.DateDiaryResponseModel
import com.sumory.model.model.diary.DiaryWriteResponseModel
import com.sumory.model.param.diary.DiaryWriteRequestParam
import com.sumory.network.datasource.diary.DiaryDataSource
import com.sumory.network.mapper.diary.request.toDto
import com.sumory.network.mapper.diary.response.toModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class DiaryRepositoryImpl @Inject constructor(
    private val diaryDataSource: DiaryDataSource
) : DiaryRepository {

    private var cached: List<AllDiaryResponseModel>? = null

    override suspend fun diaryWrite(body: DiaryWriteRequestParam): Flow<DiaryWriteResponseModel> {
        cached = null
        return diaryDataSource.diaryWrite(body.toDto()).map { it.toModel() }
    }

    override suspend fun getDateDiary(date: String): Flow<List<DateDiaryResponseModel>> {
        return diaryDataSource.getDateDiary(date)
            .map { list -> list.map { it.toModel() } }
    }

    override suspend fun getAllDiary(forceRefresh: Boolean): List<AllDiaryResponseModel> {
        if (cached != null && !forceRefresh) return cached!!

        val dtoList = diaryDataSource.getAllDiary().first()
        val modelList = dtoList.map { it.toModel() }
        cached = modelList
        return modelList
    }
}
