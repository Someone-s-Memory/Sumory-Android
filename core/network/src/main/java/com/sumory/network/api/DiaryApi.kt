package com.sumory.network.api

import com.sumory.network.dto.diary.request.DiaryRequest
import com.sumory.network.dto.diary.request.DiaryWriteRequest
import com.sumory.network.dto.diary.response.AllDiaryResponse
import com.sumory.network.dto.diary.response.DiaryResponse
import com.sumory.network.dto.diary.response.DiaryWriteResponse
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST

interface DiaryApi {

    @POST("diary/write")
    suspend fun diaryWrite(
        @Header("Authorization") token: String,
        @Body body: DiaryWriteRequest
    ): DiaryWriteResponse


    @GET("diary")
    suspend fun getDateDiary(
        @Header("Authorization") token: String,
        @Body body: DiaryRequest
    ): List<DiaryResponse>

    @GET("diary/all")
    suspend fun getAllDiary(
        @Header("Authorization") token: String
    ): List<AllDiaryResponse>
}