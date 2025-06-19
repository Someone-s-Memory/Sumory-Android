package com.sumory.signup.viewmodel.uistate

sealed interface SignUpUiState {
    object Idle : SignUpUiState
    object Loading : SignUpUiState
    data class Success(val message: String) : SignUpUiState
    data class Error(val errorMessage: String) : SignUpUiState
}