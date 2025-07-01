package com.sumory.diary.viewmodel.uistate

sealed class DiaryWriteUiState {
    object Idle : DiaryWriteUiState()
    object Success : DiaryWriteUiState()
    data class Error(val message: String) : DiaryWriteUiState()
}