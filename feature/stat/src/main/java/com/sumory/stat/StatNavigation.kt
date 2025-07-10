package com.sumory.stat

import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.sumory.stat.view.StatScreen

const val statRoute = "statRoute"

fun NavGraphBuilder.statScreen() {
    composable(statRoute) {
        StatScreen(
            feelings = mapOf(
                "행복" to 4,
                "슬픔" to 3,
                "나쁘지 않음" to 3,
                "화남" to 1
            ),
            total = 11,
            sequence = 3
        )
    }
}