package com.sumory.signup

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import com.sumory.signup.view.SignUpRoute

const val signUpRoute = "signUpRoute"

fun NavController.navigateToSignUp(navOptions: NavOptions? = null) {
    this.navigate(signUpRoute, navOptions)
}

fun NavGraphBuilder.signUpScreen(
    onBackClick: () -> Unit,
    onSignUpSuccess: () -> Unit
) {
    composable(signUpRoute) {
        SignUpRoute(
            onBackClick = onBackClick,
            onSignUpSuccess = onSignUpSuccess
        )
    }
}
