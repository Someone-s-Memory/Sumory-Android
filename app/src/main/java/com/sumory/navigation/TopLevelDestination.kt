package com.sumory.navigation

import com.sumory.home.homeRoute
import com.sumory.design_system.R

enum class TopLevelDestination (
    val unselectedIcon: Int,
    val iconText: String,
    val routeName: String,
) {
    Home(
        unselectedIcon = R.drawable.ic_home,
        iconText = "홈",
        routeName = homeRoute
    ),

    Calendar(
        unselectedIcon = R.drawable.ic_calendar,
        iconText = "캘린더",
        routeName = homeRoute
    ),

    Diary(
        unselectedIcon = R.drawable.ic_diary,
        iconText = "모아보기",
        routeName = homeRoute
    ),

    Stat(
        unselectedIcon = R.drawable.ic_stat,
        iconText = "통계",
        routeName = homeRoute
    ),

    Profile(
        unselectedIcon = R.drawable.ic_profile,
        iconText = "프로필",
        routeName = homeRoute
    ),
}