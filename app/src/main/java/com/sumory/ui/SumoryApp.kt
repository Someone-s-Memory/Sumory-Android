package com.sumory.sumory.ui

import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.windowsizeclass.WindowSizeClass
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import com.sumory.design_system.theme.SumoryTheme
import com.sumory.navigation.SumoryNavHost
import com.sumory.navigation.TopLevelDestination
import com.sumory.ui.SumoryAppState
import com.sumory.ui.rememberSumoryAppState

@Composable
fun SumoryApp(
    windowSizeClass: WindowSizeClass,
    appState: SumoryAppState = rememberSumoryAppState(windowSizeClass = windowSizeClass) // 화면 크기에 맞게 앱 상태를 기억합니다.
) {
    SumoryTheme  { _, _ ->
        Scaffold(
            containerColor = Color.Transparent,
            contentColor = Color.White,
            contentWindowInsets = WindowInsets(0, 0, 0, 0),
            bottomBar = {
                SumoryBottomBar(
                    destinations = appState.topLevelDestinations,
                    currentDestination = appState.currentDestinationRoute,
                    onNavigateTo = { destination ->
                        if (appState.currentDestinationRoute != destination.routeName) {
                            appState.navController.navigate(destination.routeName) {
                                popUpTo(appState.navController.graph.startDestinationId) {
                                    saveState = true
                                }
                                launchSingleTop = true
                                restoreState = true
                            }
                        }
                    }
                )
            }
        ) { paddingValues ->
            SumoryNavHost(
                modifier = Modifier.padding(paddingValues),
                navController = appState.navController
            )
        }
    }
}

@Composable
fun SumoryBottomBar(
    destinations: List<TopLevelDestination>,
    currentDestination: String,
    onNavigateTo: (TopLevelDestination) -> Unit,
) {
    NavigationBar {
        destinations.forEach { destination ->
            NavigationBarItem(
                selected = currentDestination == destination.routeName,
                onClick = { onNavigateTo(destination) },
                icon = {
                    Icon(
                        painter = painterResource(id = destination.unselectedIcon),
                        contentDescription = destination.iconText
                    )
                },
                label = {
                    Text(text = destination.iconText)
                }
            )
        }
    }
}
