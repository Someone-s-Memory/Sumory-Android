package com.sumory.diary.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sumory.data.repository.diary.DiaryRepository
import com.sumory.model.param.diary.DiaryWriteRequestParam
import com.sumory.diary.viewmodel.uistate.DiaryWriteUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DiaryWriteViewModel @Inject constructor(
    private val diaryRepository: DiaryRepository
) : ViewModel() {

    private val _title = MutableStateFlow("")
    val title = _title.asStateFlow()

    private val _content = MutableStateFlow("")
    val content = _content.asStateFlow()

    private val _selectedEmotion = MutableStateFlow<String?>(null)
    val selectedEmotion = _selectedEmotion.asStateFlow()

    private val _selectedWeather = MutableStateFlow<String?>(null)
    val selectedWeather = _selectedWeather.asStateFlow()

    private val _diaryWriteState = MutableStateFlow<DiaryWriteUiState>(DiaryWriteUiState.Idle)
    val diaryWriteState = _diaryWriteState.asStateFlow()

    fun updateTitle(value: String) {
        _title.value = value
    }

    fun updateContent(value: String) {
        _content.value = value
    }

    fun selectEmotion(emoji: String) {
        _selectedEmotion.value = emoji
    }

    fun selectWeather(emoji: String) {
        _selectedWeather.value = emoji
    }

    private val emotionMap = mapOf(
        "😊" to "기쁨",
        "😢" to "슬픔",
        "😳" to "당황",
        "😠" to "화남",
        "😆" to "즐거움",
        "🤔" to "생각중"
    )

    private val weatherMap = mapOf(
        "🌞" to "맑음",
        "☁️" to "구름",
        "🌧️" to "비",
        "❄️" to "눈",
        "🌩️" to "천둥번개",
        "🌈" to "무지개"
    )

    fun postDiary(date: String, picture: String = "") {
        val param = DiaryWriteRequestParam(
            title = _title.value,
            content = _content.value,
            feeling = emotionMap[_selectedEmotion.value] ?: "",
            weather = weatherMap[_selectedWeather.value] ?: "",
            date = date,
            picture = picture
        )

        viewModelScope.launch {
            try {
                diaryRepository.diaryWrite(param).collect {
                    _diaryWriteState.value = DiaryWriteUiState.Success
                }
            } catch (e: Exception) {
                _diaryWriteState.value = DiaryWriteUiState.Error(e.localizedMessage ?: "일기 저장에 실패하였습니다.")
            }
        }
    }

    fun resetWriteState() {
        _diaryWriteState.value = DiaryWriteUiState.Idle
    }
}
