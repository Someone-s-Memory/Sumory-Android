package com.sumory.splash.view

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import com.sumory.splash.viewmodel.SplashViewModel

@Composable
fun SplashRoute(
    viewModel: SplashViewModel = hiltViewModel(),
    onNavigateToHome: () -> Unit,
    onNavigateToSignIn: () -> Unit
) {
    val loginState by viewModel.loginState.collectAsState()

    LaunchedEffect(Unit) {
        viewModel.checkLogin()
    }

    when (loginState) {
        null -> SplashScreen()
        true -> onNavigateToHome()
        false -> onNavigateToSignIn()
    }
}


@Composable
fun SplashScreen() {
    CircularProgressIndicator(modifier = Modifier.fillMaxSize())
}
