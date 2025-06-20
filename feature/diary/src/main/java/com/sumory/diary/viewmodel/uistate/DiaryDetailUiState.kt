package com.sumory.diary.viewmodel.uistate

import com.sumory.model.entity.diary.DiaryDetailEntity

sealed interface DiaryDetailUiState {
    object Loading : DiaryDetailUiState
    data class Success(val data: DiaryDetailEntity) : DiaryDetailUiState
    data class Error(val message: String) : DiaryDetailUiState
}
