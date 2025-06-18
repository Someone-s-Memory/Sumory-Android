package com.sumory.signin.view

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
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
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.sumory.design_system.component.textfield.SumoryTextField
import com.sumory.design_system.icon.EyeIcon
import com.sumory.design_system.theme.SumoryTheme
import com.sumory.signin.viewmodel.SignInViewModel
import com.sumory.signin.viewmodel.uistate.SignInUiState
import com.sumory.ui.DevicePreviews

@Composable
fun SignInRoute(
    onSignInClick: () -> Unit,
    onSignInSuccess: () -> Unit,
    viewModel: SignInViewModel = hiltViewModel()
) {
    var passwordVisible by remember { mutableStateOf(false) }

    val uiState by viewModel.signInState.collectAsState()

    LaunchedEffect(uiState) {
        if (uiState is SignInUiState.Success) {
            onSignInSuccess()
        }
    }

    SignInScreen(
        id = viewModel.id.value,
        password = viewModel.password.value,
        passwordVisible = passwordVisible,
        onIdChange = viewModel::onIdChange,
        onPasswordChange = viewModel::onPasswordChange,
        onPasswordVisibilityToggle = { passwordVisible = !passwordVisible },
        onSignInClick = { viewModel.signIn() },
        onSignUpClick = onSignInClick
    )
}



@Composable
fun SignInScreen(
    modifier: Modifier = Modifier,
    id: String,
    password: String,
    passwordVisible: Boolean,
    onIdChange: (String) -> Unit,
    onPasswordChange: (String) -> Unit,
    onPasswordVisibilityToggle: () -> Unit,
    onSignInClick: () -> Unit,
    onSignUpClick: () -> Unit
) {
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

            Spacer(modifier = modifier.height(8.dp))

            Text(
                text = "Sumory",
                style = typography.titleMedium1,
                color = colors.black
            )

            Spacer(modifier = modifier.height(4.dp))

            Text(
                text = "로그인하여 시작하세요",
                style = typography.bodyRegular2,
                color = colors.gray500
            )

            Spacer(modifier = modifier.height(32.dp))

            SumoryTextField(
                textState = id,
                placeHolder = "아이디",
                onTextChange = onIdChange,
                icon = {}
            )

            SumoryTextField(
                textState = password,
                placeHolder = "비밀번호",
                onTextChange = onPasswordChange,
                icon = {
                    EyeIcon(
                        isSelected = passwordVisible,
                        modifier = modifier.clickable { onPasswordVisibilityToggle() },
                        tint = colors.black
                    )
                },
                visualTransformation = if (passwordVisible) VisualTransformation.None else PasswordVisualTransformation()
            )

            Spacer(modifier = modifier.height(15.dp))

            Button(
                onClick = onSignInClick,
                modifier = modifier
                    .fillMaxWidth()
                    .height(48.dp),
                shape = RoundedCornerShape(12.dp),
                colors = ButtonDefaults.buttonColors(containerColor = colors.darkPink)
            ) {
                Text("로그인", color = colors.white)
            }

            Spacer(modifier = modifier.height(10.dp))

            TextButton(onClick = onSignUpClick) {
                Text("회원가입", color = colors.main)
            }
        }
    }
}

@DevicePreviews
@Composable
fun SignInScreenPreview() {
    SignInScreen(
        id = "previewId",
        password = "123456",
        passwordVisible = false,
        onIdChange = {},
        onPasswordChange = {},
        onPasswordVisibilityToggle = {},
        onSignInClick = {},
        onSignUpClick = {}
    )
}