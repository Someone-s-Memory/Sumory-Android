package com.sumory.signup.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sumory.data.repository.auth.AuthRepository
import com.sumory.model.param.auth.SignUpRequestParam
import com.sumory.signup.viewmodel.uistate.SignUpUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SignUpViewModel @Inject constructor(
    private val authRepository: AuthRepository,
) : ViewModel() {
    private val _signUpState = MutableStateFlow<SignUpUiState>(SignUpUiState.Idle)
    val signUpState: StateFlow<SignUpUiState> = _signUpState

    private val _userId = MutableStateFlow("")
    val userId: StateFlow<String> = _userId.asStateFlow()

    private val _nickname = MutableStateFlow("")
    val nickname: StateFlow<String> = _nickname.asStateFlow()

    private val _password = MutableStateFlow("")
    val password: StateFlow<String> = _password.asStateFlow()

    private val _passwordVisible = MutableStateFlow(false)
    val passwordVisible: StateFlow<Boolean> = _passwordVisible.asStateFlow()

    private val _checkPassword = MutableStateFlow("")
    val checkPassword: StateFlow<String> = _checkPassword.asStateFlow()

    private val _checkPasswordVisible = MutableStateFlow(false)
    val checkPasswordVisible: StateFlow<Boolean> = _checkPasswordVisible.asStateFlow()

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

    fun resetError() {
        if (signUpState.value is SignUpUiState.Error) {
            _signUpState.value = SignUpUiState.Idle
        }
    }

    fun onUserIdChange(newUserId: String) {
        _userId.value = newUserId
    }

    fun onNicknameChange(newNickname: String) {
        _nickname.value = newNickname
    }

    fun onPasswordChange(newPassword: String) {
        _password.value = newPassword
    }

    fun onPasswordVisibleChange(isVisible: Boolean) {
        _passwordVisible.value = isVisible
    }

    fun onCheckPasswordChange(newCheckPassword: String) {
        _checkPassword.value = newCheckPassword
    }

    fun onCheckPasswordVisibleChange(isVisible: Boolean) {
        _checkPasswordVisible.value = isVisible
    }
}