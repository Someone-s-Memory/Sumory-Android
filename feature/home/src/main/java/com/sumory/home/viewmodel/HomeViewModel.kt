package com.sumory.home.viewmodel

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(): ViewModel() {
    private val _petName = MutableStateFlow("")
    val petName: StateFlow<String> = _petName

    private val _affinity = MutableStateFlow(0)
    val affinity: StateFlow<Int> = _affinity
}