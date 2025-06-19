package com.sumory.signup.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sumory.data.repository.auth.AuthRepository
import com.sumory.model.param.auth.SignUpRequestParam
import com.sumory.signup.viewmodel.uistate.SignUpUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SignUpViewModel @Inject constructor(
    private val authRepository: AuthRepository,
) : ViewModel() {
    private val _signUpState = MutableStateFlow<SignUpUiState>(SignUpUiState.Idle)
    val signUpState: StateFlow<SignUpUiState> = _signUpState

    fun signUp(
        userId: String,
        password: String,
        passwordCheck: String,
        nickname: String
    ) {
        viewModelScope.launch {
            _signUpState.value = SignUpUiState.Loading
            authRepository.signUp(
                SignUpRequestParam(userId, password, passwordCheck, nickname)
            )
                .catch { e ->
                    _signUpState.value = SignUpUiState.Error(e.message ?: "알 수 없는 오류")
                }
                .collect {
                    _signUpState.value = SignUpUiState.Success(it.msg)
                }
        }
    }
}