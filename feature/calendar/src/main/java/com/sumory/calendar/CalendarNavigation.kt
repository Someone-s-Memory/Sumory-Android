package com.sumory.calendar

import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.sumory.calendar.view.CalendarRoute

const val calendarRoute = "calendarRoute"

fun NavGraphBuilder.calendarScreen(
    onDiaryClick: (Int) -> Unit
) {
    composable(calendarRoute) {
        CalendarRoute (
            onDiaryClick = onDiaryClick,
            onWriteClick = {}
        )
    }
}