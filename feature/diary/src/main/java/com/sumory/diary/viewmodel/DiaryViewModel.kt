package com.sumory.diary.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sumory.data.repository.diary.DiaryRepository
import com.sumory.model.model.diary.DiaryAllResponseModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DiaryViewModel @Inject constructor(
    private val diaryRepository: DiaryRepository
) : ViewModel() {

    private val _diaryList = MutableStateFlow<List<DiaryAllResponseModel>>(emptyList())
    val diaryList: StateFlow<List<DiaryAllResponseModel>> = _diaryList

    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading

    private val _errorMessage = MutableStateFlow<String?>(null)
    val errorMessage: StateFlow<String?> = _errorMessage

    init {
        fetchDiaries()
    }

    fun fetchDiaries() {
        viewModelScope.launch {
            _isLoading.value = true
            _errorMessage.value = null

            try {
                diaryRepository.getDiaryAll()
                    .collect { list ->
                        _diaryList.value = list
                    }
            } catch (e: Exception) {
                _diaryList.value = emptyList()
                _errorMessage.value = null
            } finally {
                _isLoading.value = false
            }
        }
    }
}
