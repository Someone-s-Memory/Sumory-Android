package com.sumory.setting.view

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.sumory.design_system.theme.SumoryTheme
import com.sumory.setting.viewmodel.SettingViewModel
import com.sumory.ui.DevicePreviews

@Composable
fun SettingRoute(
    onSignOutSuccess: () -> Unit,
    viewModel: SettingViewModel = hiltViewModel()
) {
    val signOutState by viewModel.signOutState.collectAsState()

    // 로그아웃 완료 시 콜백 실행
    LaunchedEffect(signOutState) {
        if (signOutState) {
            onSignOutSuccess()
            viewModel.resetSignOutState()
        }
    }

    SettingScreen(
        onSignOutClick = { viewModel.signOut() }
    )
}

@Composable
fun SettingScreen(
    modifier: Modifier = Modifier,
    onSignOutClick: () -> Unit
){
    SumoryTheme { colors, typography ->
        Box(
            Modifier
            .fillMaxSize()
            .background(colors.white),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = "설정 화면",
                color = colors.black
            )
        }
        Button(
            onClick = onSignOutClick
        ) {
            Text("로그아웃")
        }
    }
}

@DevicePreviews
@Composable
fun SettingScreenPreview(){
    SettingScreen(
        onSignOutClick = {}
    )
}