package com.sumory.diary.viewmodel

import android.util.Log
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sumory.data.repository.diary.DiaryRepository
import com.sumory.model.model.diary.DateDiaryResponseModel
import com.sumory.model.param.diary.DateDiaryRequestParam
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import java.time.LocalDate
import javax.inject.Inject

@HiltViewModel
class CalendarViewModel @Inject constructor(
    private val diaryRepository: DiaryRepository
) : ViewModel() {

    private val _diaryList = MutableStateFlow<List<DateDiaryResponseModel>>(emptyList())
    val diaryList: StateFlow<List<DateDiaryResponseModel>> = _diaryList

    private val _selectedDate = mutableStateOf(LocalDate.now())
    val selectedDate: State<LocalDate> = _selectedDate

    init {
        loadDiaries(_selectedDate.value)
    }

    fun loadDiaries(date: LocalDate) {
        val dateString = date.toString()
        viewModelScope.launch {
            try {
                diaryRepository.getDateDiary(dateString).collect { list ->
                    _diaryList.value = list
                }
            } catch (e: Exception) {
                _diaryList.value = emptyList()
            }
        }
    }

    fun onDateSelected(date: LocalDate) {
        _selectedDate.value = date
        loadDiaries(date)
    }

    fun diariesOfSelectedDate(): List<DateDiaryResponseModel> {
        val selectedDateStr = selectedDate.value.toString()
        return diaryList.value.filter { it.date == selectedDateStr }
    }
}
