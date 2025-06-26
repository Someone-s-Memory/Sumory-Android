package com.sumory.calendar.view

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.sumory.calendar.view.component.CalendarDiaryItem
import com.sumory.design_system.icon.EditIcon
import com.sumory.design_system.theme.SumoryTheme
import com.sumory.model.entity.calendar.CalendarDiaryListEntity
import com.sumory.ui.DevicePreviews
import java.time.LocalDate
import java.time.YearMonth
import kotlin.math.ceil

data class CalendarDateState(
    val date: LocalDate?,
    val isSelected: Boolean,
    val isToday: Boolean,
    val hasDiary: Boolean,
    val emoji: String = ""
)

@Composable
fun CalendarRoute(
    modifier: Modifier = Modifier,
    onDiaryClick: (Int) -> Unit,
    onWriteClick: () -> Unit
) {
    var selectedDate by remember { mutableStateOf(LocalDate.now()) }
    val currentMonth = YearMonth.now()
    val today = LocalDate.now()

    // 샘플 데이터 (실제 프로젝트에선 ViewModel로부터 주입)
    val diaryList = listOf(
        CalendarDiaryListEntity(1, "산책", "날씨 좋아서 산책", "😍", "☀️", LocalDate.of(2025, 6, 6)),
        CalendarDiaryListEntity(2, "비 오는 날", "집에만 있었음", "😐", "🌧️", LocalDate.of(2025, 6, 9)),
        CalendarDiaryListEntity(3, "카페에서", "회고", "😊", "☀️", LocalDate.of(2025, 6, 11))
    )

    val diaryMap = diaryList.groupBy { it.date }

    val calendarDays = generateCalendarDates(currentMonth)

    val weeks: List<List<CalendarDateState>> = calendarDays.chunked(7).map { week ->
        week.map { date ->
            val hasDiary = date != null && diaryMap.containsKey(date)
            CalendarDateState(
                date = date,
                isSelected = date == selectedDate,
                isToday = date == today,
                hasDiary = hasDiary,
                emoji = if (hasDiary) diaryMap[date]?.firstOrNull()?.emotionEmoji.orEmpty() else ""
            )
        }
    }

    val consecutiveDays = 3

    CalendarScreen(
        modifier = modifier,
        currentMonth = currentMonth,
        selectedDate = selectedDate,
        weeks = weeks,
        diariesOfSelectedDate = diaryMap[selectedDate].orEmpty(),
        consecutiveDays = consecutiveDays,
        onDateSelected = { selectedDate = it },
        onDiaryClick = onDiaryClick,
        onWriteClick = onWriteClick
    )
}

@Composable
fun CalendarScreen(
    modifier: Modifier = Modifier,
    currentMonth: YearMonth,
    selectedDate: LocalDate,
    weeks: List<List<CalendarDateState>>,
    diariesOfSelectedDate: List<CalendarDiaryListEntity>,
    consecutiveDays: Int,
    onDateSelected: (LocalDate) -> Unit,
    onDiaryClick: (Int) -> Unit,
    onWriteClick: () -> Unit
) {
    SumoryTheme { colors, typography ->
        Column(
            modifier
                .fillMaxSize()
                .background(colors.white)
        ) {
            Row(
                modifier = modifier
                    .fillMaxWidth()
                    .padding(horizontal = 20.dp, vertical = 25.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = "${currentMonth.year}년 ${currentMonth.monthValue}월",
                    color = colors.black,
                    style = typography.titleBold2
                )
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

            Row(
                modifier = modifier
                    .fillMaxWidth()
                    .padding(horizontal = 8.dp)
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
                            modifier = modifier
                                .weight(1f)
                                .padding(2.dp),
                            contentAlignment = Alignment.Center
                        ) {
                            val size = maxWidth.coerceAtMost(90.dp)
                            Box(
                                modifier = modifier
                                    .size(size)
                                    .clip(RoundedCornerShape(12.dp))
                                    .background(
                                        if (dayState.isSelected) colors.pinkSoftBackground else colors.white
                                    )
                                    .then(
                                        if (border != null)
                                            modifier.border(border, RoundedCornerShape(12.dp))
                                        else modifier
                                    )
                                    .clickable(enabled = date != null) {
                                        date?.let { onDateSelected(it) }
                                    },
                                contentAlignment = Alignment.Center
                            ) {
                                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                                    Text(
                                        text = date?.dayOfMonth?.toString() ?: "",
                                        style = typography.bodyRegular1,
                                        color = colors.black
                                    )
                                    if (dayState.hasDiary) {
                                        Text(dayState.emoji, fontSize = 12.sp)
                                    }
                                }
                            }
                        }
                    }
                }
            }

            Spacer(modifier.height(16.dp))

            Column(
                modifier = modifier.fillMaxSize(),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Box(
                    modifier = modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp)
                ) {
                    Text(
                        text = "${selectedDate.year}. ${selectedDate.monthValue}. ${selectedDate.dayOfMonth}",
                        style = typography.titleBold2,
                        color = colors.black,
                        modifier = modifier.align(Alignment.Center)
                    )
                    EditIcon(
                        modifier = modifier
                            .align(Alignment.CenterEnd)
                            .clickable { onWriteClick() },
                        tint = colors.black
                    )
                }

                Spacer(modifier.height(8.dp))

                if (diariesOfSelectedDate.isNotEmpty()) {
                    Column(
                        modifier = modifier.padding(horizontal = 16.dp),
                        verticalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        diariesOfSelectedDate.forEach { diary ->
                            CalendarDiaryItem(
                                item = diary,
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
        CalendarDiaryListEntity(1, "산책", "날씨 좋아서 산책", "😍", "☀️", LocalDate.of(2025, 6, 6)),
        CalendarDiaryListEntity(2, "비 오는 날", "집에만 있었음", "😐", "🌧️", LocalDate.of(2025, 6, 9)),
        CalendarDiaryListEntity(3, "카페에서", "회고", "😊", "☀️", LocalDate.of(2025, 6, 11))
    )
    val diaryMap = dummyList.groupBy { it.date }

    val weeks = generateCalendarDates(currentMonth).chunked(7).map { week ->
        week.map { date ->
            val hasDiary = date != null && diaryMap.containsKey(date)
            CalendarDateState(
                date = date,
                isSelected = date == selectedDate,
                isToday = date == today,
                hasDiary = hasDiary,
                emoji = if (hasDiary) diaryMap[date]?.firstOrNull()?.emotionEmoji.orEmpty() else ""
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
        onWriteClick = {}
    )
}
