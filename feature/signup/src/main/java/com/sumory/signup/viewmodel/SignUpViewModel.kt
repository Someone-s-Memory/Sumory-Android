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

    private val _passwordLengthError = MutableStateFlow(false)
    val passwordLengthError: StateFlow<Boolean> = _passwordLengthError.asStateFlow()

    private val _passwordLengthErrorMessage = MutableStateFlow("")
    val passwordLengthErrorMessage: StateFlow<String> = _passwordLengthErrorMessage.asStateFlow()

    private val _email = MutableStateFlow("")
    val email: StateFlow<String> = _email.asStateFlow()

    fun signUp(
        userId: String,
        password: String,
        passwordCheck: String,
        nickname: String,
        email: String
    ) {
        viewModelScope.launch {
            _signUpState.value = SignUpUiState.Loading
            authRepository.signUp(
                SignUpRequestParam(userId, password, passwordCheck, nickname, email)
            )
                .catch { e ->
                    _signUpState.value = SignUpUiState.Error("동일한 아이디가 존재하거나 이메일 형식을 확인하세요.")
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

    fun onPasswordVisibleChange(isVisible: Boolean) {
        _passwordVisible.value = isVisible
    }

    fun onCheckPasswordChange(newCheckPassword: String) {
        _checkPassword.value = newCheckPassword
    }

    fun onCheckPasswordVisibleChange(isVisible: Boolean) {
        _checkPasswordVisible.value = isVisible
    }

    fun onPasswordChange(newPassword: String) {
        _password.value = newPassword
        validatePasswordLength(newPassword)
    }

    fun onEmailChange(newEmail: String) {
        _email.value = newEmail
    }

    private fun validatePasswordLength(password: String) {
        if (password.isNotBlank() && password.length < 8) {
            _passwordLengthError.value = true
            _passwordLengthErrorMessage.value = "비밀번호는 8자 이상이어야 합니다."
        } else {
            _passwordLengthError.value = false
            _passwordLengthErrorMessage.value = ""
        }
    }
}