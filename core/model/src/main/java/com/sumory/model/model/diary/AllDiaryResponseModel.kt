package com.sumory.model.model.diary

data class AllDiaryResponseModel (
    val id: Int,
    val title: String,
    val content: String,
    val feeling: String,
    val weather: String,
    val date: String,
    val pictures: List<String>,
    val userID: String
)