package com.sumory.diary.viewmodel

import android.content.Context
import android.net.Uri
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sumory.data.repository.diary.DiaryRepository
import com.sumory.diary.viewmodel.uistate.DiaryWriteUiState
import com.sumory.model.param.diary.DiaryUpdateRequestParam
import com.sumory.model.param.diary.DiaryWriteRequestParam
import com.sumory.model.type.DiaryFeeling
import com.sumory.model.type.DiaryWeather
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.io.File
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import javax.inject.Inject

@HiltViewModel
class DiaryWriteViewModel @Inject constructor(
    private val diaryRepository: DiaryRepository,
    savedStateHandle: SavedStateHandle
) : ViewModel() {

    private val diaryId: Int? = savedStateHandle.get<Int>("diaryId")

    private val _originTitle = MutableStateFlow("")
    val originTitle = _originTitle.asStateFlow()

    private val _title = MutableStateFlow("")
    val title = _title.asStateFlow()

    private val _content = MutableStateFlow("")
    val content = _content.asStateFlow()

//    private val _contentLength = MutableStateFlow(0)
//    val contentLength = _contentLength.asStateFlow()

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

    private val _diaryDate = MutableStateFlow<LocalDate>(LocalDate.now())
    val diaryDate: StateFlow<LocalDate> = _diaryDate.asStateFlow()

    init {
        if (diaryId != null && diaryId != -1) {
            loadDiary(diaryId)
        }
    }

    private fun loadDiary(id: Int) {
        viewModelScope.launch {
            try {
                val diary = diaryRepository.getDiaryDetail(id)
                _originTitle.value = diary.title
                _title.value = diary.title
                _content.value = diary.content
                _selectedEmotion.value = DiaryFeeling.values().find { it.value == diary.feeling }
                _selectedWeather.value = DiaryWeather.values().find { it.value == diary.weather }
                _diaryDate.value = LocalDate.parse(diary.date, DateTimeFormatter.ISO_DATE)
                _imageUris.value = diary.pictures.map { Uri.parse(it) }
            } catch (e: Exception) {
                _diaryWriteState.value = DiaryWriteUiState.Error("일기를 불러오는데 실패했습니다.")
            }
        }
    }

    fun updateTitle(value: String) {
        _title.value = value
    }

    fun updateContent(value: String) {
        _content.value = value
//        if (value.length <= 1000) {
//            _content.value = value
//            _contentLength.value = value.length
//        }
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

    // ✅ Uri → 내부 캐시에 복사하여 파일 경로 반환
    private fun copyUriToInternalStorage(context: Context, uri: Uri): String? {
        return try {
            val inputStream = context.contentResolver.openInputStream(uri) ?: return null
            val fileName = "image_${System.currentTimeMillis()}.jpg"
            val file = File(context.cacheDir, fileName)
            file.outputStream().use { output ->
                inputStream.copyTo(output)
            }
            file.absolutePath
        } catch (e: Exception) {
            null
        }
    }

    fun postDiary(context: Context) {
        // 1. 유효성 검사
        when {
            _title.value.isBlank() -> {
                _diaryWriteState.value = DiaryWriteUiState.Error("제목을 입력해주세요.")
                return
            }
            _content.value.isBlank() -> {
                _diaryWriteState.value = DiaryWriteUiState.Error("내용을 입력해주세요.")
                return
            }
//            _content.value.length > 1000 -> {
//                _diaryWriteState.value = DiaryWriteUiState.Error("내용은 1000자를 초과할 수 없습니다.")
//                return
//            }
            _selectedEmotion.value == null -> {
                _diaryWriteState.value = DiaryWriteUiState.Error("감정을 선택해주세요.")
                return
            }
            _selectedWeather.value == null -> {
                _diaryWriteState.value = DiaryWriteUiState.Error("날씨를 선택해주세요.")
                return
            }
        }

        // 2. 이미지 Uri → 내부 저장소로 복사 후 경로 추출
        val picturePaths = _imageUris.value.mapNotNull { uri ->
            if (uri.scheme?.startsWith("http") == true) {
                uri.toString()
            } else {
                copyUriToInternalStorage(context, uri)
            }
        }

        val date = _diaryDate.value.format(DateTimeFormatter.ISO_DATE)

        // 3. 요청 파라미터 생성
        val param = DiaryWriteRequestParam(
            title = _title.value,
            content = _content.value,
            feeling = _selectedEmotion.value?.value ?: "",
            weather = _selectedWeather.value?.value ?: "",
            date = date,
            pictures = picturePaths
        )

        // 4. 업로드 요청
        viewModelScope.launch {
            try {
                if (diaryId != null && diaryId != -1) {
                    val updateParam = DiaryUpdateRequestParam(
                        title = _originTitle.value,
                        content = _content.value,
                        feeling = _selectedEmotion.value?.value ?: "",
                        weather = _selectedWeather.value?.value ?: "",
                        date = date,
                        pictures = picturePaths,
                        change = _title.value
                    )
                    diaryRepository.updateDiary(updateParam).collect {
                        _diaryWriteState.value = DiaryWriteUiState.Success
                    }
                } else {
                    diaryRepository.diaryWrite(param).collect {
                        _diaryWriteState.value = DiaryWriteUiState.Success
                    }
                }
            } catch (e: Exception) {
                _diaryWriteState.value = DiaryWriteUiState.Error("동일한 제목이 있습니다. 다른 제목을 사용해주세요.")
            }
        }
    }

    fun resetWriteState() {
        _diaryWriteState.value = DiaryWriteUiState.Idle
    }
}
