package com.sumory.diary.viewmodel

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sumory.data.repository.diary.DiaryRepository
import com.sumory.model.model.diary.AllDiaryResponseModel
import com.sumory.model.model.diary.DateDiaryResponseModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import java.time.LocalDate
import java.time.YearMonth
import javax.inject.Inject

@HiltViewModel
class CalendarViewModel @Inject constructor(
    private val diaryRepository: DiaryRepository
) : ViewModel() {

    private val _allDiaries = MutableStateFlow<List<AllDiaryResponseModel>>(emptyList())
    val allDiaries: StateFlow<List<AllDiaryResponseModel>> = _allDiaries

    private val _dateDiaries = MutableStateFlow<List<DateDiaryResponseModel>>(emptyList())
    val dateDiaries: StateFlow<List<DateDiaryResponseModel>> = _dateDiaries

    private val _selectedDate = mutableStateOf(LocalDate.now())
    val selectedDate: State<LocalDate> = _selectedDate

    private val _currentMonth = MutableStateFlow(YearMonth.now())
    val currentMonth: StateFlow<YearMonth> = _currentMonth

    private val _consecutiveDays = mutableStateOf(0)
    val consecutiveDays: State<Int> = _consecutiveDays

    init {
        _selectedDate.value = LocalDate.now()
        loadAllDiaries(forceRefresh = false)
        loadDateDiaries(_selectedDate.value, forceRefresh = false)
    }

    fun incrementMonth() {
        _currentMonth.value = _currentMonth.value.plusMonths(1)
        loadAllDiaries(forceRefresh = false)
    }

    fun decrementMonth() {
        _currentMonth.value = _currentMonth.value.minusMonths(1)
        loadAllDiaries(forceRefresh = false)
    }

    fun todayMonth() {
        _currentMonth.value = YearMonth.now()
        loadAllDiaries(forceRefresh = false)
    }

    fun onDateSelected(date: LocalDate, forceRefresh: Boolean = false) {
        _selectedDate.value = date
        loadDateDiaries(date, forceRefresh)
    }

    fun resetSelectedDateToToday() {
        _selectedDate.value = LocalDate.now()
        loadDateDiaries(_selectedDate.value, forceRefresh = true)
        loadAllDiaries(forceRefresh = true)
    }

    fun loadDataForCurrentSelection() {
        loadDateDiaries(_selectedDate.value, forceRefresh = false)
        loadAllDiaries(forceRefresh = false)
    }

    private fun loadAllDiaries(forceRefresh: Boolean = false) {
        viewModelScope.launch {
            try {
                diaryRepository.getAllDiary(forceRefresh).collect {
                    _allDiaries.value = it
                    calculateConsecutiveDays(_allDiaries.value)
                }
            } catch (e: Exception) {
                _allDiaries.value = emptyList()
            }
        }
    }

    private fun loadDateDiaries(date: LocalDate, forceRefresh: Boolean = false) {
        viewModelScope.launch {
            try {
                diaryRepository.getDateDiary(date.toString(), forceRefresh).collect {
                    _dateDiaries.value = it
                }
            } catch (e: Exception) {
                _dateDiaries.value = emptyList()
            }
        }
    }

    private fun calculateConsecutiveDays(list: List<AllDiaryResponseModel>) {
        val dates = list.mapNotNull { runCatching { LocalDate.parse(it.date) }.getOrNull() }
            .sortedDescending()

        var count = 0
        var current = LocalDate.now()

        while (dates.contains(current)) {
            count++
            current = current.minusDays(1)
        }

        _consecutiveDays.value = count
    }
}