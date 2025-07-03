package com.sumory.diary.viewmodel.uistate

sealed class DiaryDetailUiState {
    object Idle : DiaryDetailUiState()
    object Success : DiaryDetailUiState()
    data class Error(val message: String) : DiaryDetailUiState()
}
