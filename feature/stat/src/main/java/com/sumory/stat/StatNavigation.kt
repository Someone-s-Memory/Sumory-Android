package com.sumory.stat

import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.sumory.stat.view.StatScreen

const val statRoute = "statRoute"

fun NavGraphBuilder.statScreen() {
    composable(statRoute) {
        StatScreen()
    }
}