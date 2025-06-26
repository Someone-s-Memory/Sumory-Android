package com.sumory.setting.view

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.sumory.design_system.component.dialog.SumoryDialog
import com.sumory.design_system.theme.SumoryTheme
import com.sumory.setting.view.component.SettingItem
import com.sumory.setting.viewmodel.SettingViewModel
import com.sumory.setting.viewmodel.uistate.SignOutUiState
import com.sumory.ui.DevicePreviews

@Composable
fun SettingRoute(
    onSignOutSuccess: () -> Unit,
    viewModel: SettingViewModel = hiltViewModel()
) {
    val signOutState by viewModel.signOutState.collectAsState()

    var showDialog by remember { mutableStateOf(false) }

    val context = LocalContext.current

    LaunchedEffect(signOutState) {
        when (signOutState) {
            is SignOutUiState.Success -> {
                onSignOutSuccess()
                viewModel.resetSignOutState()
            }
            is SignOutUiState.Error -> {
                Toast.makeText(context, (signOutState as SignOutUiState.Error).message, Toast.LENGTH_SHORT).show()
                viewModel.resetSignOutState()
            }
            else -> Unit
        }
    }

    SettingScreen(
        onAlertSettingClick = {},
        onSignOutClick = { showDialog = true }
    )

    if (showDialog) {
        SumoryDialog(
            title = "로그아웃",
            message = "정말 로그아웃 하시겠습니까?",
            onConfirm = {
                showDialog = false
                viewModel.signOut()
            },
            onDismiss = { showDialog = false }
        )
    }
}

@Composable
fun SettingScreen(
    modifier: Modifier = Modifier,
    onAlertSettingClick: () -> Unit,
    onSignOutClick: () -> Unit
) {
    SumoryTheme { colors, typography ->

        Column(
            modifier
                .fillMaxSize()
                .background(colors.white)
                .padding(start = 20.dp, end = 20.dp, top = 24.dp),
        ) {
            Text(
                text = "설정",
                color = colors.black,
                style = typography.titleMedium1
            )

            Spacer(modifier = modifier.padding(top = 24.dp))

            SettingItem(
                title = "알림 설정",
                subtitle = "일기 작성 리마인드",
                onClick = onAlertSettingClick
            )

            Spacer(modifier = modifier.height(15.dp))

            SettingItem(
                title = "로그아웃",
                subtitle = "계정에서 로그아웃",
                onClick = onSignOutClick
            )
        }
    }
}

@DevicePreviews
@Composable
private fun SettingScreenPreview(){
    SettingScreen(
        onAlertSettingClick = {},
        onSignOutClick = {}
    )
}