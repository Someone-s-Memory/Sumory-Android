package com.sumory.signin.viewmodel.uistate

sealed class SignInUiState {
    object Idle : SignInUiState()
    object Loading : SignInUiState()
    data class Success(val user: Any) : SignInUiState() // SignInResponseModel로 변경 가능
    data class Error(val message: String) : SignInUiState()
}