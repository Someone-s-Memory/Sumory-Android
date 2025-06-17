package com.sumory.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import com.sumory.calendar.calendarScreen
import com.sumory.diary.diaryScreen
import com.sumory.home.homeRoute
import com.sumory.home.homeScreen
import com.sumory.profile.profileScreen
import com.sumory.setting.settingScreen
import com.sumory.signin.signInScreen
import com.sumory.signup.signUpScreen
import com.sumory.stat.statScreen
import com.sumory.store.storeScreen

@Composable
fun SumoryNavHost(
    modifier: Modifier = Modifier,
    navController: NavHostController,
    startDestination: String = homeRoute,
) {
    NavHost(
        modifier = modifier,
        navController = navController,
        startDestination = startDestination,
    ) {
        signInScreen()

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