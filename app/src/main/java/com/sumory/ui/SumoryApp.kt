package com.sumory.sumory.ui

import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.windowsizeclass.WindowSizeClass
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import com.sumory.design_system.component.navigation.SumoryNavigationBar
import com.sumory.design_system.component.navigation.SumoryNavigationBarItem
import com.sumory.design_system.component.navigation.SumoryTopBar
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
    val currentRoute = appState.currentDestinationRoute

    // 로그인/회원가입 화면은 제외할 라우트 목록
    val shouldShowBars = currentRoute !in listOf("login", "signup")

    SumoryTheme { _, _ ->
        Scaffold(
            topBar = {
                if (shouldShowBars) {
                    SumoryTopBar(
                        title = "일기 펫",
                        coinCount = 150,
                        currentRoute = currentRoute,
                        onNavigateTo = { route ->
                            appState.navController.navigate(route) {
                                popUpTo(appState.navController.graph.startDestinationId) {
                                    saveState = true
                                }
                                launchSingleTop = true
                                restoreState = true
                            }
                        }
                    )
                }
            },
            bottomBar = {
                if (shouldShowBars) {
                    SumoryBottomBar(
                        topLevelDestinations = appState.topLevelDestinations, // 최상위 목적지 목록을 전달
                        onNavigateToDestination = appState::navigateToTopLevelDestination, // 네비게이션 함수
                        currentDestination = appState.currentDestination // 현재 목적지 정보
                    )
                }
            },
            containerColor = Color.Transparent,
            contentColor = Color.White,
            contentWindowInsets = WindowInsets(0, 0, 0, 0),
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
    modifier: Modifier = Modifier,
    topLevelDestinations: List<TopLevelDestination>,
    onNavigateToDestination: (TopLevelDestination) -> Unit, // 사용자가 클릭했을 때 호출될 콜백
    currentDestination: String? // 현재 네비게이션 목적지
) {
    SumoryTheme { colors, typography ->
        SumoryNavigationBar {
            SumoryNavigationBar(modifier = modifier) {
                // 각 최상위 목적지에 대한 아이템을 생성합니다.
                topLevelDestinations.forEach { destination ->
                    // icon painter를 미리 변수로 추출
                    val iconPainter = painterResource(id = destination.unSelectedIcon)
                    // 현재 목적지가 선택된 상태인지 확인
                    val isSelected = destination.routeName == currentDestination

                    SumoryNavigationBarItem(
                        selected = isSelected,
                        onClick = { onNavigateToDestination(destination) },
                        icon = { Icon(painter = iconPainter, contentDescription = null) },
                        selectedIcon = { Icon(painter = iconPainter, contentDescription = null, tint = colors.main) },
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
}

