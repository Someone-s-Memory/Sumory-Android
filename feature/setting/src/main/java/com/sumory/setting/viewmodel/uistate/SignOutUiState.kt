package com.sumory.setting.viewmodel.uistate

sealed interface SignOutUiState {
    object Idle : SignOutUiState
    object Success : SignOutUiState
    data class Error(val message: String) : SignOutUiState
}