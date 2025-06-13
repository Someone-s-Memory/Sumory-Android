package com.sumory.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import com.sumory.home.homeRoute
import com.sumory.home.homeScreen

@Composable
fun SumoryNavHost(
    modifier: Modifier = Modifier,
    navController: NavHostController,
    startDestination: String = homeRoute,
) {
    NavHost(
        modifier = modifier,
        navController = navController,
        startDestination = startDestination,
    ) {
        homeScreen()
    }
}