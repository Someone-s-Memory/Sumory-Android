package com.sumory.diary.viewmodel

import android.content.Context
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
import java.io.File
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

    // Uri -> 로컬 파일 경로 변환 함수
    private fun getRealPathFromUri(context: Context, uri: Uri): String? {
        var path: String? = null
        val projection = arrayOf(android.provider.MediaStore.Images.Media.DATA)
        val cursor = context.contentResolver.query(uri, projection, null, null, null)
        cursor?.use {
            if (it.moveToFirst()) {
                val columnIndex = it.getColumnIndexOrThrow(android.provider.MediaStore.Images.Media.DATA)
                path = it.getString(columnIndex)
            }
        }
        return path
    }

    fun postDiary(date: String, context: Context) {
        val picturePaths = _imageUris.value.mapNotNull { uri ->
            val path = getRealPathFromUri(context, uri)
            if (path != null && File(path).exists()) path else null
        }

        val param = DiaryWriteRequestParam(
            title = _title.value,
            content = _content.value,
            feeling = _selectedEmotion.value?.value ?: "",
            weather = _selectedWeather.value?.value ?: "",
            date = date,
            picture = picturePaths
        )

        viewModelScope.launch {
            try {
                diaryRepository.diaryWrite(param).collect {
                    _diaryWriteState.value = DiaryWriteUiState.Success
                }
            } catch (e: Exception) {
                _diaryWriteState.value = DiaryWriteUiState.Error("일기 저장 실패: ${e.message}")
            }
        }
    }

    fun resetWriteState() {
        _diaryWriteState.value = DiaryWriteUiState.Idle
    }
}