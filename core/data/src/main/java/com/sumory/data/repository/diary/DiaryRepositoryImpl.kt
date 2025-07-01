package com.sumory.data.repository.diary

import com.sumory.model.model.diary.DiaryAllResponseModel
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

    private var cached: List<DiaryAllResponseModel>? = null

    override suspend fun diaryWrite(body: DiaryWriteRequestParam): Flow<DiaryWriteResponseModel> {
        cached = null
        return diaryDataSource.diaryWrite(body.toDto()).map { it.toModel() }
    }

    override suspend fun getDiaryAll(forceRefresh: Boolean): List<DiaryAllResponseModel> {
        if (cached != null && !forceRefresh) return cached!!

        val dtoList = diaryDataSource.getDiaryAll().first()
        val modelList = dtoList.map { it.toModel() }
        cached = modelList
        return modelList
    }
}
