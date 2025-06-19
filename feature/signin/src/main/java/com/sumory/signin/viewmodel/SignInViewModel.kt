package com.sumory.signin.viewmodel

import android.util.Log
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sumory.data.repository.auth.AuthRepository
import com.sumory.model.param.auth.SignInRequestParam
import com.sumory.signin.viewmodel.uistate.SignInUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SignInViewModel @Inject constructor(
    private val authRepository: AuthRepository,
    private val savedStateHandle: SavedStateHandle,
) : ViewModel() {

    companion object {
        private const val USER_ID = "userId"
        private const val PASSWORD = "password"
    }

    val userId: StateFlow<String> = savedStateHandle.getStateFlow(USER_ID, "")
    val password: StateFlow<String> = savedStateHandle.getStateFlow(PASSWORD, "")

    private val _signInState = MutableStateFlow<SignInUiState>(SignInUiState.Idle)
    val signInState: StateFlow<SignInUiState> = _signInState

    fun onIdChange(newId: String) {
        savedStateHandle[USER_ID] = newId
    }

    fun onPasswordChange(newPassword: String) {
        savedStateHandle[PASSWORD] = newPassword
    }

    fun signIn() {
        viewModelScope.launch {
            _signInState.value = SignInUiState.Loading
            Log.d("SignInViewModel", "로그인 요청 시작: id=${userId.value}, password=${password.value}")

            authRepository.signIn(SignInRequestParam(userId.value, password.value))
                .catch { e ->
                    Log.e("SignInViewModel", "로그인 실패: ${e.message}", e)
                    _signInState.value = SignInUiState.Error("아이디 혹은 비밀번호가 올바르지 않습니다.")
                }
                .collectLatest { result ->
                    Log.d("SignInViewModel", "로그인 성공: $result")
                    _signInState.value = SignInUiState.Success(result)
                }
        }
    }

    fun resetError() {
        if (signInState.value is SignInUiState.Error) {
            _signInState.value = SignInUiState.Idle
        }
    }
}
