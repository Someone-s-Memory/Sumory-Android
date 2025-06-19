package com.sumory.setting.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sumory.data.repository.auth.AuthRepository
import com.sumory.setting.viewmodel.uistate.SignOutUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SettingViewModel @Inject constructor(
    private val authRepository: AuthRepository
) : ViewModel() {

    private val _signOutState = MutableStateFlow<SignOutUiState>(SignOutUiState.Idle)
    val signOutState: StateFlow<SignOutUiState> = _signOutState

    fun signOut() {
        viewModelScope.launch {
            try {
                authRepository.signOut()
                    .collect {
                        _signOutState.value = SignOutUiState.Success
                    }
            } catch (e: Exception) {
                _signOutState.value = SignOutUiState.Error(e.message ?: "로그아웃 실패")
            }
        }
    }

    fun resetSignOutState() {
        _signOutState.value = SignOutUiState.Idle
    }
}