package com.sumory.home

import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.sumory.home.view.HomeScreenRoute

const val homeRoute = "homeRoute"

fun NavGraphBuilder.homeScreen() {
    composable(homeRoute) {
        HomeScreenRoute()
    }
}