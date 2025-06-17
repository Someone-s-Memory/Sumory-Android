package com.sumory.signin

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import com.sumory.signin.view.SignInScreen

const val signInRoute = "signInRoute"

fun NavController.navigateToSignIn(navOptions: NavOptions? = null) {
    this.navigate(signInRoute, navOptions)
}

fun NavGraphBuilder.signInScreen(
    onSignUpClick: () -> Unit,
) {
    composable(signInRoute) {
        SignInScreen(
            onSignUpClick = onSignUpClick
        )
    }
}