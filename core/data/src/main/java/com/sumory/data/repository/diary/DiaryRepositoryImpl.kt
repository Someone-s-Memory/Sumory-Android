package com.sumory.data.repository.diary

import com.sumory.model.model.diary.AllDiaryResponseModel
import com.sumory.model.model.diary.DateDiaryResponseModel
import com.sumory.model.model.diary.DiaryDeleteResponseModel
import com.sumory.model.model.diary.DiaryDetailResponseModel
import com.sumory.model.model.diary.DiaryUpdateResponseModel
import com.sumory.model.model.diary.DiaryWriteResponseModel
import com.sumory.model.param.diary.DiaryUpdateRequestParam
import com.sumory.model.param.diary.DiaryWriteRequestParam
import com.sumory.network.datasource.diary.DiaryDataSource
import com.sumory.network.mapper.diary.request.toDto
import com.sumory.network.mapper.diary.response.toModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class DiaryRepositoryImpl @Inject constructor(
    private val diaryDataSource: DiaryDataSource
) : DiaryRepository {

    private val cached = MutableStateFlow<List<AllDiaryResponseModel>?>(null)
    private val dateDiaryCache = mutableMapOf<String, List<DateDiaryResponseModel>>()

    override suspend fun diaryWrite(body: DiaryWriteRequestParam): Flow<DiaryWriteResponseModel> {
        cached.value = null
        dateDiaryCache.clear()
        return diaryDataSource.diaryWrite(body.toDto()).map { it.toModel() }
    }

    override suspend fun getDateDiary(date: String, forceRefresh: Boolean): Flow<List<DateDiaryResponseModel>> {
        if (!forceRefresh) {
            dateDiaryCache[date]?.let { cachedList ->
                return kotlinx.coroutines.flow.flowOf(cachedList)
            }
        }

        return diaryDataSource.getDateDiary(date)
            .map { list ->
                val mapped = list.map { it.toModel() }
                dateDiaryCache[date] = mapped
                mapped
            }
    }

    override suspend fun getAllDiary(forceRefresh: Boolean): Flow<List<AllDiaryResponseModel>> = flow {
        if (cached.value != null && !forceRefresh) {
            emit(cached.value!!)
            return@flow
        }
        val dtoList = diaryDataSource.getAllDiary().first()
        val modelList = dtoList.map { it.toModel() }
        cached.value = modelList
        emit(modelList)
    }

    override suspend fun getDiaryDetail(diaryId: Int): DiaryDetailResponseModel {
        return diaryDataSource.getDiaryDetail(diaryId)
            .map { it.toModel() }
            .first()
    }

    override suspend fun deleteDiary(date: String, title: String): Flow<DiaryDeleteResponseModel> {
        return diaryDataSource
            .deleteDiary(date, title)
            .map { it.toModel() }
    }

    override suspend fun updateDiary(body: DiaryUpdateRequestParam): Flow<DiaryUpdateResponseModel> {
        cached.value = null
        dateDiaryCache.clear()
        return diaryDataSource.updateDiary(body.toDto()).map { it.toModel() }
    }

    // 캐시 수동 초기화 함수
    fun clearDateDiaryCache(date: String) {
        dateDiaryCache.remove(date)
    }

    override suspend fun clearAllCache() {
        cached.value = null
        dateDiaryCache.clear()
    }
}
