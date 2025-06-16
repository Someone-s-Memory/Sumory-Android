package com.sumory.home

import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.sumory.home.view.HomeScreen

const val homeRoute = "homeRoute"

fun NavGraphBuilder.homeScreen() {
    composable(homeRoute) {
        HomeScreen(
            petName = "나의 펫",
            affinity = 15
        )
    }
}