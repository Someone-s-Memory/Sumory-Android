package com.sumory.network.api

import com.sumory.network.dto.diary.request.DiaryWriteRequest
import com.sumory.network.dto.diary.response.AllDiaryResponse
import com.sumory.network.dto.diary.response.DateDiaryResponse
import com.sumory.network.dto.diary.response.DiaryDetailResponse
import com.sumory.network.dto.diary.response.DiaryWriteResponse
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST
import retrofit2.http.Query

interface DiaryApi {

    @POST("diary/write")
    suspend fun diaryWrite(
        @Header("Authorization") token: String,
        @Body body: DiaryWriteRequest
    ): DiaryWriteResponse


    @GET("diary")
    suspend fun getDateDiary(
        @Header("Authorization") token: String,
        @Query("date") date: String
    ): List<DateDiaryResponse>

    @GET("diary/all")
    suspend fun getAllDiary(
        @Header("Authorization") token: String
    ): List<AllDiaryResponse>

    @GET("diary/detail")
    suspend fun getDiaryDetail(
        @Header("Authorization") token: String,
        @Query("diaryId") diaryId: Int
    ): DiaryDetailResponse
}