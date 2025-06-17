package com.sumory.signup

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import com.sumory.signup.view.SignUpScreen

const val signUpRoute = "signUpRoute"

fun NavController.navigationToSignUp(navOptions: NavOptions? = null) {
    this.navigate(signUpRoute, navOptions)
}

fun NavGraphBuilder.signUpScreen() {
    composable(signUpRoute) {
        SignUpScreen()
    }
}