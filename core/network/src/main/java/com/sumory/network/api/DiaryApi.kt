package com.sumory.network.api

import com.sumory.network.dto.diary.request.DiaryWriteRequest
import com.sumory.network.dto.diary.response.DiaryAllResponse
import com.sumory.network.dto.diary.response.DiaryWriteResponse
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

interface DiaryApi {
    @POST("diary/write")
    suspend fun diaryWrite(
        @Body body: DiaryWriteRequest
    ): DiaryWriteResponse

    @GET("diary/all")
    suspend fun getDiaryAll(): List<DiaryAllResponse>
}