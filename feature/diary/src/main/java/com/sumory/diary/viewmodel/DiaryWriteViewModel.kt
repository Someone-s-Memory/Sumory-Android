package com.sumory.diary.viewmodel

import android.net.Uri
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sumory.data.repository.diary.DiaryRepository
import com.sumory.model.param.diary.DiaryWriteRequestParam
import com.sumory.diary.viewmodel.uistate.DiaryWriteUiState
import com.sumory.model.type.DiaryFeeling
import com.sumory.model.type.DiaryWeather
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

    private val _selectedEmotion = MutableStateFlow<DiaryFeeling?>(null)
    val selectedEmotion = _selectedEmotion.asStateFlow()

    private val _selectedWeather = MutableStateFlow<DiaryWeather?>(null)
    val selectedWeather = _selectedWeather.asStateFlow()

    private val _diaryWriteState = MutableStateFlow<DiaryWriteUiState>(DiaryWriteUiState.Idle)
    val diaryWriteState = _diaryWriteState.asStateFlow()

    private val _imageUris = MutableStateFlow<List<Uri>>(emptyList())
    val imageUris: StateFlow<List<Uri>> = _imageUris

    private val _openGalleryEvent = MutableSharedFlow<Unit>()
    val openGalleryEvent = _openGalleryEvent.asSharedFlow()

    fun updateTitle(value: String) {
        _title.value = value
    }

    fun updateContent(value: String) {
        _content.value = value
    }

    fun selectEmotion(feeling: DiaryFeeling) {
        _selectedEmotion.value = feeling
    }

    fun selectWeather(weather: DiaryWeather) {
        _selectedWeather.value = weather
    }

    fun addImage(uri: Uri) {
        _imageUris.update { it + uri }
    }

    fun removeImage(uri: Uri) {
        _imageUris.update { it - uri }
    }

    fun onImageAddClick() {
        viewModelScope.launch {
            _openGalleryEvent.emit(Unit)
        }
    }

    fun triggerGalleryLaunch() {
        viewModelScope.launch {
            _openGalleryEvent.emit(Unit)
        }
    }

    fun postDiary(date: String, picture: String = "") {
        val param = DiaryWriteRequestParam(
            title = _title.value,
            content = _content.value,
            feeling = _selectedEmotion.value?.value ?: "",
            weather = _selectedWeather.value?.value ?: "",
            date = date,
            picture = picture
        )

        viewModelScope.launch {
            try {
                diaryRepository.diaryWrite(param).collect {
                    _diaryWriteState.value = DiaryWriteUiState.Success
                }
            } catch (e: Exception) {
                _diaryWriteState.value = DiaryWriteUiState.Error("일기 저장에 실패하였습니다.")
            }
        }
    }

    fun resetWriteState() {
        _diaryWriteState.value = DiaryWriteUiState.Idle
    }
}
