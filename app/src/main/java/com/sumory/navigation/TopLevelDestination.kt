package com.sumory.navigation

import com.sumory.diary.calendarRoute
import com.sumory.home.homeRoute
import com.sumory.design_system.R
import com.sumory.diary.diaryRoute
import com.sumory.profile.profileRoute
import com.sumory.stat.statRoute

enum class TopLevelDestination (
    val unSelectedIcon: Int,
    val iconText: String,
    val routeName: String,
) {
    Calendar(
        unSelectedIcon = R.drawable.ic_calendar,
        iconText = "캘린더",
        routeName = calendarRoute
    ),

    Diary(
        unSelectedIcon = R.drawable.ic_diary,
        iconText = "모아보기",
        routeName = diaryRoute
    ),

    Home(
        unSelectedIcon = R.drawable.ic_home,
        iconText = "홈",
        routeName = homeRoute
    ),

    Stat(
        unSelectedIcon = R.drawable.ic_stat,
        iconText = "통계",
        routeName = statRoute
    ),

    Profile(
        unSelectedIcon = R.drawable.ic_profile,
        iconText = "프로필",
        routeName = profileRoute
    ),
}