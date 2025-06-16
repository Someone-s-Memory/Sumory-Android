package com.sumory.profile

import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.sumory.profile.view.ProfileScreen

const val profileRoute = "profileRoute"

fun NavGraphBuilder.profileScreen() {
    composable(profileRoute) {
        ProfileScreen()
    }
}