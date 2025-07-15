package com.sumory.stat.view

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.sumory.design_system.theme.SumoryTheme
import com.sumory.stat.view.component.DiaryStatusCard
import com.sumory.stat.view.component.EmotionStatCard
import com.sumory.stat.view.component.MonthPickerBottomSheet
import com.sumory.ui.DevicePreviews
import java.time.YearMonth

sealed class StatRange {
    data class Monthly(val year: Int, val month: Int) : StatRange()
    object Total : StatRange()

    val label: String
        get() = when (this) {
            is Monthly -> "${month}월"
            is Total -> "전체"
        }

    val title: String
        get() = when (this) {
            is Monthly -> "${month}월의 통계"
            is Total -> "전체 통계"
        }
}

data class StatsUiModel(
    val feelings: Map<String, Int>,
    val total: Int,
    val sequence: Int
)

@Composable
fun StatScreen(
    statsMap: Map<StatRange, StatsUiModel>,
    selectedRange: StatRange,
    onSelectRange: (StatRange) -> Unit
) {
    val currentStats = statsMap[selectedRange] ?: return

    // selectedRange가 Monthly면 해당 YearMonth, Total이면 null
    val currentYearMonth = (selectedRange as? StatRange.Monthly)?.let {
        YearMonth.of(it.year, it.month)
    }

    // diaryRange는 전체 데이터 범위(예: 2025-06 ~ 2025-07) 고정 또는 동적으로 받아올 수도 있음
    val diaryRange = YearMonth.of(2025, 6)..YearMonth.of(2025, 7)

    SumoryTheme { colors, typography ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(colors.gray50)
                .padding(horizontal = 16.dp)
        ) {
            Spacer(Modifier.height(16.dp))

            Text(
                text = "통계",
                style = typography.titleMedium1,
                color = colors.black
            )

            Spacer(Modifier.height(10.dp))

            MonthPickerBottomSheet(
                current = currentYearMonth,
                diaryRange = diaryRange,
                onSelect = { ym ->
                    if (ym == null) {
                        onSelectRange(StatRange.Total)
                    } else {
                        onSelectRange(StatRange.Monthly(ym.year, ym.monthValue))
                    }
                }
            )

            Spacer(Modifier.height(10.dp))

            EmotionStatCard(
                title = selectedRange.title,
                feelings = currentStats.feelings,
                total = currentStats.total
            )

            Spacer(Modifier.height(24.dp))

            DiaryStatusCard(
                total = currentStats.total,
                sequence = currentStats.sequence
            )
        }
    }
}

@DevicePreviews
@Composable
fun StatScreenPreview() {
    val july = StatRange.Monthly(2025, 7)
    val june = StatRange.Monthly(2025, 6)
    val total = StatRange.Total

    val statMap = mapOf(
        july to StatsUiModel(
            feelings = mapOf("행복" to 4, "슬픔" to 2, "화남" to 1),
            total = 7,
            sequence = 3
        ),
        june to StatsUiModel(
            feelings = mapOf("행복" to 3, "슬픔" to 1, "나쁘지 않음" to 2),
            total = 6,
            sequence = 2
        ),
        total to StatsUiModel(
            feelings = mapOf("행복" to 10, "슬픔" to 5, "나쁘지 않음" to 3, "화남" to 2),
            total = 20,
            sequence = 7
        )
    )

    var selectedRange by remember { mutableStateOf<StatRange>(july) }

    StatScreen(
        statsMap = statMap,
        selectedRange = selectedRange,
        onSelectRange = { selectedRange = it }
    )
}