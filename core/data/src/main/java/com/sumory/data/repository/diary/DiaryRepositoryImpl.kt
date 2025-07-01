package com.sumory.data.repository.diary

import com.sumory.model.model.diary.DiaryAllResponseModel
import com.sumory.model.model.diary.DiaryWriteResponseModel
import com.sumory.model.param.diary.DiaryWriteRequestParam
import com.sumory.network.datasource.diary.DiaryDataSource
import com.sumory.network.mapper.diary.request.toDto
import com.sumory.network.mapper.diary.response.toModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.transform
import javax.inject.Inject

class DiaryRepositoryImpl @Inject constructor(
    private val diaryDataSource: DiaryDataSource
) : DiaryRepository {

    override suspend fun diaryWrite(body: DiaryWriteRequestParam): Flow<DiaryWriteResponseModel> {
        return diaryDataSource.diaryWrite(body.toDto()).transform {
            emit(it.toModel())
        }
    }

    override suspend fun getDiaryAll(): Flow<List<DiaryAllResponseModel>> {
        return diaryDataSource.getDiaryAll()
            .map { dtoList ->
                dtoList.map { it.toModel() }
            }
    }
}
