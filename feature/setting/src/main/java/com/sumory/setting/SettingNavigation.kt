package com.sumory.setting

import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.sumory.setting.view.SettingRoute

const val settingRoute = "settingRoute"

fun NavGraphBuilder.settingScreen(
    onSignOutSuccess: () -> Unit
) {
    composable(settingRoute) {
        SettingRoute(
            onSignOutSuccess = onSignOutSuccess
        )
    }
}