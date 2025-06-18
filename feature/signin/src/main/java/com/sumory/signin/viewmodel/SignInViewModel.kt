package com.sumory.signin.viewmodel

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sumory.data.repository.auth.AuthRepository
import com.sumory.model.param.auth.SignInRequestParam
import com.sumory.signin.viewmodel.uistate.SignInUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SignInViewModel @Inject constructor(
    private val authRepository: AuthRepository
) : ViewModel() {

    var id = mutableStateOf("")
        private set

    var password = mutableStateOf("")
        private set

    private val _signInState = MutableStateFlow<SignInUiState>(SignInUiState.Idle)
    val signInState: StateFlow<SignInUiState> = _signInState

    fun onIdChange(newId: String) {
        id.value = newId
    }

    fun onPasswordChange(newPassword: String) {
        password.value = newPassword
    }

    fun signIn() {
        viewModelScope.launch {
            _signInState.value = SignInUiState.Loading
            authRepository.signIn(
                SignInRequestParam(id.value, password.value)
            ).catch {
                _signInState.value = SignInUiState.Error(it.message ?: "알 수 없는 오류")
            }.collect { result ->
                _signInState.value = SignInUiState.Success(result)
            }
        }
    }
}
