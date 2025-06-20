//package com.sumory.diary.viewmodel
//
//import androidx.lifecycle.ViewModel
//import androidx.lifecycle.viewModelScope
//import com.sumory.diary.viewmodel.uistate.DiaryDetailUiState
//import dagger.hilt.android.lifecycle.HiltViewModel
//import kotlinx.coroutines.flow.MutableStateFlow
//import kotlinx.coroutines.flow.StateFlow
//import kotlinx.coroutines.launch
//import javax.inject.Inject
//
//@HiltViewModel
//class DiaryDetailViewModel @Inject constructor(
//    private val diaryRepository: DiaryRepository
//): ViewModel() {
//    private val _uiState = MutableStateFlow<DiaryDetailUiState>(DiaryDetailUiState.Loading)
//    val uiState: StateFlow<DiaryDetailUiState> = _uiState
//
//    fun loadDiaryDetail(id: Int) {
//        viewModelScope.launch {
//            runCatching {
//                diaryRepository.getDiaryDetail(id)
//            }.onSuccess {
//                _uiState.value = DiaryDetailUiState.Success(it)
//            }.onFailure {
//                _uiState.value = DiaryDetailUiState.Error("불러오기 실패")
//            }
//        }
//    }
//}