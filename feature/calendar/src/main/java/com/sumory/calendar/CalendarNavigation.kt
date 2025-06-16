package com.sumory.calendar

import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.sumory.calendar.view.CalendarScreen

const val calendarRoute = "calendarRoute"

fun NavGraphBuilder.calendarScreen() {
    composable(calendarRoute) {
        CalendarScreen()
    }
}