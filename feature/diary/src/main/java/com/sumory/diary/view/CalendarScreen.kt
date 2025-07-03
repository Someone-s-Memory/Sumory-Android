package com.sumory.diary.view

import android.annotation.SuppressLint
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.sumory.design_system.icon.EditIcon
import com.sumory.design_system.icon.LeftArrowIcon
import com.sumory.design_system.icon.RightArrowIcon
import com.sumory.design_system.theme.SumoryTheme
import com.sumory.diary.view.component.CalendarDiaryItem
import com.sumory.diary.viewmodel.CalendarViewModel
import com.sumory.diary.viewmodel.mapper.iconRes
import com.sumory.model.mapper.diary.toCalendarDiaryListEntity
import com.sumory.model.mapper.diary.toDiaryFeeling
import com.sumory.model.model.diary.DateDiaryResponseModel
import com.sumory.ui.DevicePreviews
import java.time.LocalDate
import java.time.YearMonth
import kotlin.math.ceil

@SuppressLint("StateFlowValueCalledInComposition")
data class CalendarDateState(
    val date: LocalDate?,
    val isSelected: Boolean,
    val isToday: Boolean,
    val hasDiary: Boolean,
    val feeling: String = "",
    val imageUrl: String? = null
)

@Composable
fun CalendarRoute(
    viewModel: CalendarViewModel = hiltViewModel(),
    onDiaryClick: (Int) -> Unit,
    onWriteClick: () -> Unit
) {
    val allDiaries by viewModel.allDiaries.collectAsState()
    val dateDiaries by viewModel.dateDiaries.collectAsState()
    val selectedDate by viewModel.selectedDate
    val currentMonth by viewModel.currentMonth.collectAsState()
    val consecutiveDays by viewModel.consecutiveDays

    val today = LocalDate.now()

    val diaryMap = allDiaries
        .mapNotNull {
            try {
                LocalDate.parse(it.date) to it
            } catch (e: Exception) {
                null
            }
        }
        .groupBy({ it.first }, { it.second })

    val calendarDays = generateCalendarDates(currentMonth)

    val weeks: List<List<CalendarDateState>> = calendarDays.chunked(7).map { week ->
        week.map { date ->
            val hasDiary = date != null && diaryMap.containsKey(date)
            CalendarDateState(
                date = date,
                isSelected = date == selectedDate,
                isToday = date == today,
                hasDiary = hasDiary,
                feeling = if (hasDiary) diaryMap[date]?.firstOrNull()?.feeling.orEmpty() else "",
                imageUrl = diaryMap[date]?.firstOrNull()?.pictures?.firstOrNull()
            )
        }
    }

    val lifecycleOwner = LocalLifecycleOwner.current

    DisposableEffect(lifecycleOwner) {
        val observer = LifecycleEventObserver { _, event ->
            if (event == Lifecycle.Event.ON_RESUME) {
                viewModel.resetSelectedDateToToday()
            }
        }
        lifecycleOwner.lifecycle.addObserver(observer)

        onDispose {
            lifecycleOwner.lifecycle.removeObserver(observer)
        }
    }

    CalendarScreen(
        currentMonth = currentMonth,
        selectedDate = selectedDate,
        weeks = weeks,
        diariesOfSelectedDate = dateDiaries,
        consecutiveDays = consecutiveDays,
        onDateSelected = viewModel::onDateSelected,
        onDiaryClick = onDiaryClick,
        onWriteClick = onWriteClick,
        onNextMonth = viewModel::incrementMonth,
        onPrevMonth = viewModel::decrementMonth
    )
}

@Composable
fun CalendarScreen(
    modifier: Modifier = Modifier,
    currentMonth: YearMonth,
    selectedDate: LocalDate,
    weeks: List<List<CalendarDateState>>,
    diariesOfSelectedDate: List<DateDiaryResponseModel>,
    consecutiveDays: Int,
    onDateSelected: (LocalDate) -> Unit,
    onDiaryClick: (Int) -> Unit,
    onWriteClick: () -> Unit,
    onPrevMonth: () -> Unit,
    onNextMonth: () -> Unit
) {
    SumoryTheme { colors, typography ->
        Column(
            modifier.fillMaxSize().background(colors.white)
        ) {
            Row(
                modifier = modifier
                    .fillMaxWidth()
                    .padding(horizontal = 20.dp, vertical = 25.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    LeftArrowIcon(
                        modifier = modifier
                            .clickable { onPrevMonth() }
                            .padding(horizontal = 8.dp),
                        tint = colors.black
                    )
                    Text(
                        text = "${currentMonth.year}년 ${currentMonth.monthValue}월",
                        color = colors.black,
                        style = typography.titleBold2
                    )
                    RightArrowIcon(
                        modifier = modifier
                            .clickable { onNextMonth() }
                            .padding(horizontal = 8.dp),
                        tint = colors.black
                    )
                }
                if (consecutiveDays >= 3) {
                    Box(
                        modifier = modifier
                            .background(colors.pinkSoftBackground, RoundedCornerShape(20.dp))
                            .padding(horizontal = 8.dp, vertical = 4.dp)
                    ) {
                        Text(
                            text = "🔥 ${consecutiveDays}일 연속",
                            style = typography.bodyRegular2,
                            color = colors.darkPink
                        )
                    }
                }
            }

            Row(
                modifier = modifier.fillMaxWidth().padding(horizontal = 8.dp)
            ) {
                listOf("일", "월", "화", "수", "목", "금", "토").forEach {
                    Text(
                        text = it,
                        modifier = modifier.weight(1f),
                        textAlign = TextAlign.Center,
                        color = colors.black,
                        style = typography.bodyRegular2
                    )
                }
            }

            Spacer(modifier.height(10.dp))

            weeks.forEach { week ->
                Row(modifier.fillMaxWidth().padding(horizontal = 8.dp)) {
                    week.forEach { dayState ->
                        val date = dayState.date
                        val border = when {
                            dayState.hasDiary -> BorderStroke(2.dp, colors.success)
                            dayState.isToday -> BorderStroke(1.dp, colors.gray300)
                            else -> null
                        }

                        BoxWithConstraints(
                            modifier = modifier.weight(1f).padding(2.dp),
                            contentAlignment = Alignment.Center
                        ) {
                            val size = maxWidth.coerceAtMost(90.dp)
                            Box(
                                modifier = modifier
                                    .size(size)
                                    .clip(RoundedCornerShape(12.dp))
                                    .clickable(enabled = date != null) { date?.let { onDateSelected(it) } }
                                    .then(if (border != null) modifier.border(border, RoundedCornerShape(12.dp)) else modifier),
                                contentAlignment = Alignment.Center
                            ) {
                                if (!dayState.imageUrl.isNullOrEmpty()) {
                                    AsyncImage(
                                        model = ImageRequest.Builder(LocalContext.current)
                                            .data("file://${dayState.imageUrl}")
                                            .crossfade(true)
                                            .build(),
                                        contentDescription = null,
                                        contentScale = ContentScale.Crop,
                                        alpha = 0.3f,
                                        modifier = Modifier
                                            .matchParentSize()
                                            .clip(RoundedCornerShape(12.dp))
                                    )
                                } else {
                                    Box(
                                        modifier = Modifier
                                            .matchParentSize()
                                            .background(if (dayState.isSelected) colors.pinkSoftBackground else colors.white)
                                    )
                                }

                                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                                    Text(
                                        text = date?.dayOfMonth?.toString() ?: "",
                                        style = typography.bodyRegular1,
                                        color = colors.black
                                    )
                                    if (dayState.hasDiary) {
                                        val feelingEnum = dayState.feeling.toDiaryFeeling()
                                        Icon(
                                            painter = painterResource(id = feelingEnum.iconRes()),
                                            contentDescription = dayState.feeling,
                                            modifier = Modifier.size(24.dp)
                                        )
                                    }
                                }
                            }
                        }
                    }
                }
            }

            Spacer(modifier.height(16.dp))

            Column(
                modifier.fillMaxSize(),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Box(modifier.fillMaxWidth().padding(horizontal = 16.dp)) {
                    Text(
                        text = "${selectedDate.year}. ${selectedDate.monthValue}. ${selectedDate.dayOfMonth}",
                        style = typography.titleBold2,
                        color = colors.black,
                        modifier = modifier.align(Alignment.Center)
                    )
                    EditIcon(
                        modifier = modifier.align(Alignment.CenterEnd).clickable { onWriteClick() },
                        tint = colors.black
                    )
                }

                Spacer(modifier.height(8.dp))

                if (diariesOfSelectedDate.isNotEmpty()) {
                    LazyColumn(
                        modifier = modifier
                            .fillMaxSize()
                            .padding(horizontal = 16.dp),
                        verticalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        items(diariesOfSelectedDate, key = { it.id }) { diary ->
                            CalendarDiaryItem(
                                item = diary.toCalendarDiaryListEntity(),
                                onClick = { onDiaryClick(diary.id) }
                            )
                        }
                    }
                } else {
                    Text(
                        text = "작성된 일기가 없어요 ☁️",
                        color = colors.black,
                        style = typography.bodyRegular2,
                        modifier = modifier.padding(horizontal = 16.dp)
                    )
                }
            }
        }
    }
}

fun generateCalendarDates(yearMonth: YearMonth): List<LocalDate?> {
    val firstDay = yearMonth.atDay(1)
    val daysInMonth = yearMonth.lengthOfMonth()
    val startOffset = (firstDay.dayOfWeek.value % 7)
    val totalCells = ceil((startOffset + daysInMonth) / 7.0).toInt() * 7

    return buildList {
        repeat(startOffset) { add(null) }
        for (i in 1..daysInMonth) add(yearMonth.atDay(i))
        repeat(totalCells - size) { add(null) }
    }
}

@DevicePreviews
@Composable
private fun CalendarScreenPreview() {
    val currentMonth = YearMonth.of(2025, 6)
    val today = LocalDate.of(2025, 6, 26)
    var selectedDate by remember { mutableStateOf(today) }

    val dummyList = listOf(
        DateDiaryResponseModel(1, "산책", "날씨 좋아서 산책", "기쁜", "맑음", "2025-06-26", emptyList(), "user1"),
        DateDiaryResponseModel(2, "비 오는 날", "집에만 있었음", "😐", "🌧️", "2025-06-09", emptyList(), "user1"),
        DateDiaryResponseModel(3, "카페에서", "회고", "😊", "☀️", "2025-06-11", emptyList(), "user1")
    )
    val diaryMap = dummyList
        .mapNotNull { try { LocalDate.parse(it.date) to it } catch (_: Exception) { null } }
        .groupBy({ it.first }, { it.second })

    val weeks = generateCalendarDates(currentMonth).chunked(7).map { week ->
        week.map { date ->
            val hasDiary = date != null && diaryMap.containsKey(date)
            CalendarDateState(
                date = date,
                isSelected = date == selectedDate,
                isToday = date == today,
                hasDiary = hasDiary,
                feeling = if (hasDiary) diaryMap[date]?.firstOrNull()?.feeling.orEmpty() else ""
            )
        }
    }

    CalendarScreen(
        currentMonth = currentMonth,
        selectedDate = selectedDate,
        weeks = weeks,
        diariesOfSelectedDate = diaryMap[selectedDate].orEmpty(),
        consecutiveDays = 3,
        onDateSelected = { selectedDate = it },
        onDiaryClick = {},
        onWriteClick = {},
        onPrevMonth = {},
        onNextMonth = {},
    )
}
