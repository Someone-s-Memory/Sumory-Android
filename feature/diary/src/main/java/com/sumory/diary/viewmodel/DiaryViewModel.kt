package com.sumory.diary.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sumory.data.repository.diary.DiaryRepository
import com.sumory.model.model.diary.AllDiaryResponseModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

enum class DiarySortType {
    LATEST, OLDEST
}

@HiltViewModel
class DiaryViewModel @Inject constructor(
    private val diaryRepository: DiaryRepository
) : ViewModel() {

    private val _diaryList = MutableStateFlow<List<AllDiaryResponseModel>>(emptyList())
    val diaryList: StateFlow<List<AllDiaryResponseModel>> = _diaryList

    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading

    private val _errorMessage = MutableStateFlow<String?>(null)
    val errorMessage: StateFlow<String?> = _errorMessage

    private val _sortType = MutableStateFlow(DiarySortType.LATEST)
    val sortType: StateFlow<DiarySortType> = _sortType

    fun fetchDiaries(forceRefresh: Boolean = false) {
        viewModelScope.launch {
            _isLoading.value = true
            _errorMessage.value = null
            try {
                diaryRepository.getAllDiary(forceRefresh).collect { list ->
                    _diaryList.value = list
                    applySort()
                }
            } catch (e: Exception) {
                _diaryList.value = emptyList()
                _errorMessage.value = e.message
            } finally {
                _isLoading.value = false
            }
        }
    }

    fun changeSortType(type: DiarySortType) {
        _sortType.value = type
        applySort()
    }

    private fun applySort() {
        _diaryList.update { list ->
            when (_sortType.value) {
                DiarySortType.LATEST -> list.sortedByDescending { it.date }
                DiarySortType.OLDEST -> list.sortedBy { it.date }
            }
        }
    }
}