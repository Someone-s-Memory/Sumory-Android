package com.sumory.diary.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sumory.data.repository.diary.DiaryRepository
import com.sumory.diary.viewmodel.uistate.DiaryDetailUiState
import com.sumory.model.model.diary.DiaryDetailResponseModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.util.Locale
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

    private val _diaryDetailState = MutableStateFlow<DiaryDetailUiState>(DiaryDetailUiState.Idle)
    val diaryDetailState = _diaryDetailState.asStateFlow()

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

    fun deleteDiary(){
        viewModelScope.launch {
            try {
                _diaryDetail.value?.let {
                    diaryRepository.deleteDiary(it.date, it.title).collect {
                        diaryRepository.clearAllCache()
                        _diaryDetailState.value = DiaryDetailUiState.Success
                    }
                }
            } catch (e: Exception) {
                _diaryDetailState.value = DiaryDetailUiState.Error("삭제에 실패하였습니다.")
            }
        }
    }

    fun resetState() {
        _diaryDetailState.value = DiaryDetailUiState.Idle
    }

    fun formatDateWithDayOfWeek(dateString: String): String {
        val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd", Locale.KOREA)
        val date = LocalDate.parse(dateString, formatter)

        val dayOfWeekKorean = when (date.dayOfWeek) {
            java.time.DayOfWeek.MONDAY -> "월요일"
            java.time.DayOfWeek.TUESDAY -> "화요일"
            java.time.DayOfWeek.WEDNESDAY -> "수요일"
            java.time.DayOfWeek.THURSDAY -> "목요일"
            java.time.DayOfWeek.FRIDAY -> "금요일"
            java.time.DayOfWeek.SATURDAY -> "토요일"
            java.time.DayOfWeek.SUNDAY -> "일요일"
        }

        return "${date.year}년 ${date.monthValue}월 ${date.dayOfMonth}일 $dayOfWeekKorean"
    }
}
