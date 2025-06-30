package com.sumory.ui

import android.util.Log
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.windowsizeclass.WindowSizeClass
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.navigation.compose.currentBackStackEntryAsState
import com.sumory.design_system.component.navigation.SumoryNavigationBar
import com.sumory.design_system.component.navigation.SumoryNavigationBarItem
import com.sumory.design_system.component.navigation.SumoryTopBar
import com.sumory.design_system.theme.SumoryTheme
import com.sumory.diary.diaryDetailRoute
import com.sumory.diary.diaryWriteRoute
import com.sumory.home.homeRoute
import com.sumory.navigation.SumoryNavHost
import com.sumory.navigation.TopLevelDestination
import com.sumory.signin.signInRoute
import com.sumory.signup.signUpRoute
import com.sumory.splash.splashRoute

@Composable
fun SumoryApp(
    windowSizeClass: WindowSizeClass,
    appState: SumoryAppState = rememberSumoryAppState(windowSizeClass = windowSizeClass)
) {
    val currentBackStackEntry by appState.navController.currentBackStackEntryAsState()
    val currentRoute = currentBackStackEntry?.destination?.route
    Log.d("SumoryApp", "Current route: $currentRoute")

    val shouldShowBars = currentRoute !in listOf(signInRoute, signUpRoute, splashRoute, diaryDetailRoute, diaryWriteRoute)
    val shouldHandleBack = currentRoute != homeRoute &&
            currentRoute !in listOf(signInRoute, signUpRoute, splashRoute)

    BackHandler(enabled = shouldHandleBack) {
        appState.navController.navigate(homeRoute) {
            popUpTo(0) // 모든 백스택 제거
            launchSingleTop = true
        }
    }

    SumoryTheme { _, _ ->
        Scaffold(
            topBar = {
                if (shouldShowBars) {
                    SumoryTopBar(
                        title = "Sumory",
                        coinCount = 150,
                        currentRoute = currentRoute,
                        onNavigateTo = { route ->
                            appState.navController.navigate(route) {
                                popUpTo(homeRoute) {
                                    inclusive = false
                                }
                                launchSingleTop = true
                            }
                        }
                    )
                }
            },
            bottomBar = {
                if (shouldShowBars) {
                    SumoryBottomBar(
                        topLevelDestinations = appState.topLevelDestinations,
                        onNavigateToDestination = appState::navigateToTopLevelDestination,
                        currentDestination = currentRoute
                    )
                }
            },
            containerColor = Color.Transparent,
            contentColor = Color.White,
            contentWindowInsets = WindowInsets(0, 0, 0, 0),
        ) { paddingValues ->
            SumoryNavHost(
                modifier = Modifier.padding(paddingValues),
                appState = appState
            )
        }
    }
}

@Composable
fun SumoryBottomBar(
    modifier: Modifier = Modifier,
    topLevelDestinations: List<TopLevelDestination>,
    onNavigateToDestination: (TopLevelDestination) -> Unit,
    currentDestination: String?
) {
    SumoryTheme { _, typography ->
        SumoryNavigationBar {
            topLevelDestinations.forEach { destination ->
                val iconPainter = painterResource(id = destination.unSelectedIcon)
                val isSelected = destination.routeName == currentDestination

                SumoryNavigationBarItem(
                    selected = isSelected,
                    onClick = { onNavigateToDestination(destination) },
                    icon = {
                        Icon(
                            painter = iconPainter,
                            contentDescription = null
                        )
                    },
                    selectedIcon = {
                        Icon(
                            painter = iconPainter,
                            contentDescription = null
                        )
                    },
                    label = {
                        Text(
                            text = destination.iconText,
                            style = typography.captionRegular2
                        )
                    }
                )
            }
        }
    }
}
