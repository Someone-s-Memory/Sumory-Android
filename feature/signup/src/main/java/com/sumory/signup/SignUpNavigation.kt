package com.sumory.signup

import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.sumory.signup.view.SignUpScreen

const val signUpRoute = "signUpRoute"

fun NavGraphBuilder.signUpScreen() {
    composable(signUpRoute) {
        SignUpScreen()
    }
}