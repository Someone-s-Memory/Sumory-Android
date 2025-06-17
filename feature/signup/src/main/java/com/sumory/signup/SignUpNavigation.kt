package com.sumory.signup

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import com.sumory.signup.view.SignUpScreen

const val signUpRoute = "signUpRoute"

fun NavController.navigateToSignUp(navOptions: NavOptions? = null) {
    this.navigate(signUpRoute, navOptions)
}

fun NavGraphBuilder.signUpScreen(
    onBackClick: () -> Unit
) {
    composable(signUpRoute) {
        SignUpScreen(
            onBackClick = onBackClick
        )
    }
}