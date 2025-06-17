package com.sumory.signin.view

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.sumory.design_system.theme.SumoryTheme
import com.sumory.ui.DevicePreviews

@Composable
fun SignInScreen(
    modifier: Modifier = Modifier,
    onSignUpClick: () -> Unit,
) {
    SumoryTheme { colors, typography ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(colors.white),
            contentAlignment = Alignment.Center
        ) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = "로그인 화면",
                    color = colors.black,
                    style = typography.titleMedium1
                )

                Spacer(modifier = Modifier.height(16.dp))

                Button(onClick = { onSignUpClick() }) {
                    Text(text = "회원가입 하기")
                }
            }
        }
    }
}


@DevicePreviews
@Composable
fun SignInScreenPreview(){
    SignInScreen(
        onSignUpClick = {}
    )
}