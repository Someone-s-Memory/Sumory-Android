package com.sumory.signin.view

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import com.sumory.design_system.theme.SumoryTheme
import com.sumory.ui.DevicePreviews

@Composable
fun SignInScreen(
    modifier: Modifier = Modifier,
    onSignUpClick: () -> Unit,
) {
    var id by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }

    SumoryTheme { colors, typography ->
        Column(
            modifier = modifier
                .fillMaxSize()
                .background(colors.white)
                .padding(horizontal = 32.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = "🐱",
                style = typography.titleMedium1
            )

            Spacer(modifier = Modifier.height(8.dp))

            Text(
                text = "Sumory",
                style = typography.titleMedium1,
                color = colors.black
            )

            Spacer(modifier = Modifier.height(4.dp))

            Text(
                text = "로그인하여 시작하세요",
                style = typography.bodyRegular2,
                color = colors.gray500
            )

            Spacer(modifier = Modifier.height(32.dp))

            // 아이디 입력
            OutlinedTextField(
                value = id,
                onValueChange = { id = it },
                placeholder = { Text("아이디") },
                singleLine = true,
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(16.dp))

            OutlinedTextField(
                value = password,
                onValueChange = { password = it },
                placeholder = { Text("비밀번호") },
                singleLine = true,
                visualTransformation = PasswordVisualTransformation(),
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(24.dp))

            Button(
                onClick = { /* TODO: 로그인 처리 */ },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(48.dp),
                shape = RoundedCornerShape(12.dp),
                colors = ButtonDefaults.buttonColors(containerColor = colors.darkPink)
            ) {
                Text("로그인", color = colors.white)
            }

            Spacer(modifier = Modifier.height(32.dp))

            TextButton(onClick = onSignUpClick) {
                Text("회원가입", color = colors.main)
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