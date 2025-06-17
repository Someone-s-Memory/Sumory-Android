package com.sumory.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import com.sumory.calendar.calendarScreen
import com.sumory.diary.diaryScreen
import com.sumory.home.homeRoute
import com.sumory.home.homeScreen
import com.sumory.profile.profileScreen
import com.sumory.setting.settingScreen
import com.sumory.signin.signInRoute
import com.sumory.signin.signInScreen
import com.sumory.signup.navigationToSignUp
import com.sumory.signup.signUpScreen
import com.sumory.stat.statScreen
import com.sumory.store.storeScreen
import com.sumory.ui.SumoryAppState

@Composable
fun SumoryNavHost(
    modifier: Modifier = Modifier,
    startDestination: String = signInRoute,
    appState: SumoryAppState,
) {
    val navController = appState.navController
    val context = LocalContext.current

    NavHost(
        modifier = modifier,
        navController = navController,
        startDestination = startDestination,
    ) {
        signInScreen(
            onSignUpClick = navController::navigationToSignUp,
        )

        signUpScreen()

        homeScreen()

        calendarScreen()

        diaryScreen()

        statScreen()

        profileScreen()

        storeScreen()

        settingScreen()
    }
}