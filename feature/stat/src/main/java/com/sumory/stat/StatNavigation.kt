package com.sumory.stat

import androidx.compose.runtime.*
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.sumory.stat.view.*

const val statRoute = "statRoute"

fun NavGraphBuilder.statScreen() {
    composable(statRoute) {
        val july = StatRange.Monthly(2025, 7)
        val june = StatRange.Monthly(2025, 6)
        val total = StatRange.Total

        val statMap = mapOf(
            july to StatsUiModel(
                feelings = mapOf("행복" to 4, "슬픔" to 3, "나쁘지 않음" to 3, "화남" to 1),
                total = 11,
                sequence = 3
            ),
            june to StatsUiModel(
                feelings = mapOf("행복" to 2, "슬픔" to 2),
                total = 4,
                sequence = 2
            ),
            total to StatsUiModel(
                feelings = mapOf("행복" to 6, "슬픔" to 5, "나쁘지 않음" to 3, "화남" to 1),
                total = 15,
                sequence = 5
            )
        )

        var selectedRange by remember { mutableStateOf<StatRange>(july) }

        StatScreen(
            statsMap = statMap,
            selectedRange = selectedRange,
            onSelectRange = { selectedRange = it }
        )
    }
}
