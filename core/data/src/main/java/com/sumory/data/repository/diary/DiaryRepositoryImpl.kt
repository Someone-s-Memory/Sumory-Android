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
    private val dateDiaryCache = mutableMapOf<String, List<DateDiaryResponseModel>>()

    override suspend fun diaryWrite(body: DiaryWriteRequestParam): Flow<DiaryWriteResponseModel> {
        cached = null
        dateDiaryCache.clear()
        return diaryDataSource.diaryWrite(body.toDto()).map { it.toModel() }
    }

    override suspend fun getDateDiary(date: String): Flow<List<DateDiaryResponseModel>> {
        dateDiaryCache[date]?.let { cachedList ->
            return kotlinx.coroutines.flow.flowOf(cachedList)
        }

        return diaryDataSource.getDateDiary(date)
            .map { list ->
                val mapped = list.map { it.toModel() }
                dateDiaryCache[date] = mapped
                mapped
            }
    }

    override suspend fun getAllDiary(forceRefresh: Boolean): List<AllDiaryResponseModel> {
        if (cached != null && !forceRefresh) return cached!!

        val dtoList = diaryDataSource.getAllDiary().first()
        val modelList = dtoList.map { it.toModel() }
        cached = modelList
        return modelList
    }

    // 캐시 수동 초기화 함수
    fun clearDateDiaryCache(date: String) {
        dateDiaryCache.remove(date)
    }

    fun clearAllCache() {
        cached = null
        dateDiaryCache.clear()
    }
}
