package com.sumory.setting

import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.sumory.setting.view.SettingScreen

const val settingRoute = "settingRoute"

fun NavGraphBuilder.settingScreen() {
    composable(settingRoute) {
        SettingScreen()
    }
}