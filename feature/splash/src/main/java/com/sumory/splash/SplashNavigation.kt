package com.sumory.splash

import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.sumory.splash.view.SplashRoute

const val splashRoute = "splashRoute"

fun NavGraphBuilder.splashScreen(
    onNavigateToHome: () -> Unit,
    onNavigateToSignIn: () -> Unit
) {
    composable(splashRoute) {
        SplashRoute(
            onNavigateToHome = onNavigateToHome,
            onNavigateToSignIn = onNavigateToSignIn
        )
    }
}