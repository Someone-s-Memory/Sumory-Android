package com.sumory.sumory.ui

import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.windowsizeclass.WindowSizeClass
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import com.sumory.design_system.theme.SumoryTheme
import com.sumory.navigation.SumoryNavHost
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
            }
        ) { paddingValues ->
            // 네비게이션 호스트
            SumoryNavHost(
                modifier = Modifier.padding(paddingValues = paddingValues),
                navController = appState.navController,
            )
        }
    }
}