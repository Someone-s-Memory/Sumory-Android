package com.sumory.diary.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sumory.data.repository.diary.DiaryRepository
import com.sumory.model.model.diary.DiaryDetailResponseModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DiaryDetailViewModel @Inject constructor(
    private val diaryRepository: DiaryRepository
) : ViewModel() {

    private val _diaryDetail = MutableStateFlow<DiaryDetailResponseModel?>(null)
    val diaryDetail: StateFlow<DiaryDetailResponseModel?> = _diaryDetail.asStateFlow()

    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading.asStateFlow()

    private val _errorMessage = MutableStateFlow<String?>(null)
    val errorMessage: StateFlow<String?> = _errorMessage.asStateFlow()

    fun loadDiaryDetail(id: Int) {
        viewModelScope.launch {
            _isLoading.value = true
            _errorMessage.value = null
            try {
                val detailList = diaryRepository.getDiaryDetail(id)
                _diaryDetail.value = detailList
            } catch (e: Exception) {
                _errorMessage.value = "일기 상세 정보를 불러오는데 실패했습니다."
                _diaryDetail.value = null
            } finally {
                _isLoading.value = false
            }
        }
    }
}
