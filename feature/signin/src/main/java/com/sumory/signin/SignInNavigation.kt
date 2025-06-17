package com.sumory.signin

import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.sumory.signin.view.SignInScreen

const val signInRoute = "signInRoute"

fun NavGraphBuilder.signInScreen() {
    composable(signInRoute) {
        SignInScreen()
    }
}