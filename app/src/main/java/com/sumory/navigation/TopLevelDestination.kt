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
}