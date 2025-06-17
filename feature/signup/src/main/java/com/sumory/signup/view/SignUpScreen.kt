package com.sumory.signup.view

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.sumory.design_system.theme.SumoryTheme
import com.sumory.ui.DevicePreviews

@Composable
fun SignUpScreen(){
    SumoryTheme { colors, _ ->
        Box(
            Modifier
                .fillMaxSize()
                .background(colors.white),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = "회원가입 화면",
                color = colors.black
            )
        }
    }
}

@DevicePreviews
@Composable
fun SignUpScreenPreview(){
    SignUpScreen()
}