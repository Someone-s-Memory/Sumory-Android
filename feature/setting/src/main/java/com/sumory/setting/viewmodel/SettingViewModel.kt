package com.sumory.setting.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sumory.data.repository.auth.AuthRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SettingViewModel @Inject constructor(
    private val authRepository: AuthRepository
) : ViewModel() {

    private val _signOutState = MutableStateFlow<Boolean>(false)
    val signOutState: StateFlow<Boolean> = _signOutState

    fun signOut() {
        viewModelScope.launch {
            authRepository.signOut()
                .collect {
                    _signOutState.value = true
                }
        }
    }

    fun resetSignOutState() {
        _signOutState.value = false
    }
}
