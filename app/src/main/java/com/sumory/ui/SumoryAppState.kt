package com.sumory.ui

import androidx.compose.material3.windowsizeclass.WindowSizeClass
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Stable
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.sumory.home.homeRoute
import com.sumory.navigation.TopLevelDestination
import kotlinx.coroutines.CoroutineScope


@Composable
fun rememberSumoryAppState(
    windowSizeClass: WindowSizeClass, // 화면 크기 분류 정보를 받습니다.
    navController: NavHostController = rememberNavController(), // 네비게이션 컨트롤러를 생성합니다.
    coroutineScope: CoroutineScope = rememberCoroutineScope() // 코루틴 스코프를 생성합니다.
): SumoryAppState {
    // windowSizeClass와 navController, coroutineScope가 변경될 때만 재생성하는 SumoryAppState를 기억합니다.
    return remember(
        windowSizeClass,
        coroutineScope,
        navController
    ) {
        SumoryAppState(
            windowSizeClass = windowSizeClass,
            navController = navController,
            coroutineScope = coroutineScope
        )
    }
}

@Stable
class SumoryAppState(
    val windowSizeClass: WindowSizeClass,
    val navController: NavHostController,
    val coroutineScope: CoroutineScope
) {
    // 현재 목적지 route
    val currentDestination: String?
        @Composable get() = navController
            .currentBackStackEntryAsState().value?.destination?.route

    // 최상위 목적지 리스트
    val topLevelDestinations: List<TopLevelDestination> = TopLevelDestination.values().toList()

    fun navigateToTopLevelDestination(destination: TopLevelDestination) {
        navController.navigate(destination.routeName) {
            popUpTo(homeRoute) {
                inclusive = false
            }
            launchSingleTop = true
        }
    }

}