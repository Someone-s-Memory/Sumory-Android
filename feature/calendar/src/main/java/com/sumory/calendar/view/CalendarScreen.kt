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

@Composable
fun CalendarScreen() {
    val currentMonth = YearMonth.of(2025, 6)
    val today = LocalDate.now()

    // 샘플 일기 데이터
    val diaryList = listOf(
        CalendarDiaryListEntity(1, "산책", "날씨 좋아서 산책", "😍", "☀️", LocalDate.of(2025, 6, 6)),
        CalendarDiaryListEntity(2, "비 오는 날", "비가 와서 집에만 있었음", "😐", "🌧️", LocalDate.of(2025, 6, 9)),
        CalendarDiaryListEntity(3, "카페에서", "커피 마시며 회고", "😊", "☀️", LocalDate.of(2025, 6, 11)),
        CalendarDiaryListEntity(4, "기분이 별로", "이유는 모르지만 우울", "😶", "🌫️", LocalDate.of(2025, 6, 17)),
    )

    val diaryMap = diaryList.groupBy { it.date }
    var selectedDate by remember { mutableStateOf(LocalDate.now()) }
    val days = generateCalendarDates(currentMonth)

    SumoryTheme { colors, typography ->
        Column(
            Modifier
                .fillMaxSize()
                .background(colors.white)
        ) {
            // 상단 월 표시 & 연속 뱃지
            Row(
                Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp, vertical = 16.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text("2025년 6월", style = typography.titleBold2)
                Box(
                    modifier = Modifier
                        .background(colors.pinkSoftBackground, RoundedCornerShape(20.dp))
                        .padding(horizontal = 8.dp, vertical = 4.dp)
                ) {
                    Text("🔥 3일 연속", style = typography.bodyRegular2, color = colors.darkPink)
                }
            }

            // 요일 헤더
            Row(Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp)) {
                listOf("일", "월", "화", "수", "목", "금", "토").forEach {
                    Text(
                        text = it,
                        modifier = Modifier.weight(1f),
                        textAlign = TextAlign.Center,
                        style = typography.bodyRegular2
                    )
                }
            }

            // 6주 렌더링
            for (week in days.chunked(7)) {
                Row(Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 8.dp)) {
                    week.forEach { date ->
                        val isSelected = date == selectedDate
                        val isToday = date == today
                        val hasDiary = date != null && diaryMap.containsKey(date)

                        val border = when {
                            hasDiary -> BorderStroke(2.dp, colors.success)
                            isToday -> BorderStroke(1.dp, colors.gray300)
                            else -> null
                        }

                        Box(
                            modifier = Modifier
                                .weight(1f)
                                .aspectRatio(1f)
                                .padding(4.dp)
                                .clip(RoundedCornerShape(12.dp))
                                .background(
                                    if (isSelected) colors.pinkSoftBackground else colors.white
                                )
                                .then(
                                    if (border != null)
                                        Modifier.border(border, RoundedCornerShape(12.dp))
                                    else Modifier
                                )
                                .clickable(enabled = date != null) {
                                    date?.let { selectedDate = it }
                                },
                            contentAlignment = Alignment.Center
                        ) {
                            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                                Text(
                                    text = date?.dayOfMonth?.toString() ?: "",
                                    style = typography.bodyRegular1,
                                    color = colors.black
                                )
                                if (hasDiary) {
                                    val emoji = diaryMap[date]?.firstOrNull()?.emotionEmoji ?: ""
                                    Text(emoji, fontSize = 12.sp)
                                }
                            }
                        }
                    }
                }
            }

            Spacer(Modifier.height(16.dp))

            // 날짜 + 일기 리스트
            Column(
                modifier = Modifier.fillMaxSize(),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp)
                ) {
                    Text(
                        text = "${selectedDate.year}. ${selectedDate.monthValue}. ${selectedDate.dayOfMonth}",
                        style = typography.titleMedium1,
                        color = colors.black,
                        modifier = Modifier.align(Alignment.Center)
                    )
                    EditIcon(
                        modifier = Modifier
                            .align(Alignment.CenterEnd)
                            .clickable {
                                // TODO: 일기 작성 화면 이동
                            },
                        tint = colors.black
                    )
                }

                Spacer(Modifier.height(8.dp))

                val diaries = diaryMap[selectedDate]
                if (!diaries.isNullOrEmpty()) {
                    Column(
                        modifier = Modifier
                            .padding(horizontal = 16.dp),
                        verticalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        diaries.forEach { diary ->
                            CalendarDiaryItem(
                                item = diary,
                                onClick = {
                                    // TODO: 상세 화면 이동 등
                                }
                            )
                        }
                    }
                } else {
                    Text(
                        "작성된 일기가 없어요 ☁️",
                        style = typography.bodyRegular2,
                        modifier = Modifier.padding(horizontal = 16.dp)
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
fun CalendarScreenPreview() {
    CalendarScreen()
}
