package com.sumory.signup.view

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
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
import com.sumory.design_system.icon.LeftArrowIcon
import com.sumory.design_system.theme.SumoryTheme
import com.sumory.signup.viewmodel.SignUpViewModel
import com.sumory.signup.viewmodel.uistate.SignUpUiState
import com.sumory.ui.DevicePreviews

@Composable
fun SignUpRoute(
    viewModel: SignUpViewModel = hiltViewModel(),
    onBackClick: () -> Unit,
    onSignUpSuccess: () -> Unit
) {
    val signUpState by viewModel.signUpState.collectAsState()

    var id by remember { mutableStateOf("") }
    var nickname by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var passwordVisible by remember { mutableStateOf(false) }
    var checkPassword by remember { mutableStateOf("") }
    var checkPasswordVisible by remember { mutableStateOf(false) }

    val isPasswordError = password != checkPassword && checkPassword.isNotEmpty()
    val passwordErrorMessage = if (isPasswordError) "비밀번호가 일치하는지 확인해주세요" else ""

    LaunchedEffect(signUpState) {
        if (signUpState is SignUpUiState.Success) {
            onSignUpSuccess()
        }
    }

    SignUpScreen(
        id = id,
        nickname = nickname,
        password = password,
        passwordVisible = passwordVisible,
        checkPassword = checkPassword,
        checkPasswordVisible = checkPasswordVisible,
        onIdChange = { id = it },
        onNicknameChange = { nickname = it },
        onPasswordChange = { password = it },
        onPasswordVisibleChange = { passwordVisible = it },
        onCheckPasswordChange = { checkPassword = it },
        onCheckPasswordVisibleChange = { checkPasswordVisible = it },
        onBackClick = onBackClick,
        onSignUpClick = {
            if (!isPasswordError) {
                viewModel.signUp(
                    userId = id,
                    password = password,
                    passwordCheck = checkPassword,
                    nickname = nickname
                )
            }
        },
        isPasswordError = isPasswordError,
        passwordErrorMessage = passwordErrorMessage
    )
}

@Composable
fun SignUpScreen(
    modifier: Modifier = Modifier,
    id: String,
    nickname: String,
    password: String,
    passwordVisible: Boolean,
    checkPassword: String,
    checkPasswordVisible: Boolean,
    onIdChange: (String) -> Unit,
    onNicknameChange: (String) -> Unit,
    onPasswordChange: (String) -> Unit,
    onPasswordVisibleChange: (Boolean) -> Unit,
    onCheckPasswordChange: (String) -> Unit,
    onCheckPasswordVisibleChange: (Boolean) -> Unit,
    onBackClick: () -> Unit,
    onSignUpClick: () -> Unit,
    isPasswordError: Boolean,
    passwordErrorMessage: String = ""
) {
    SumoryTheme { colors, typography ->
        Box(
            modifier = modifier
                .fillMaxSize()
                .background(colors.white)
        ) {
            LeftArrowIcon(
                modifier = modifier
                    .padding(24.dp)
                    .clickable { onBackClick() },
                tint = colors.black
            )
            Column(
                modifier = modifier
                    .fillMaxSize()
                    .padding(horizontal = 32.dp),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = "회원가입",
                    style = typography.titleMedium1,
                    color = colors.black
                )

                Spacer(modifier = modifier.height(50.dp))

                SumoryTextField(
                    textState = nickname,
                    placeHolder = "닉네임",
                    onTextChange = onNicknameChange,
                    icon = {}
                )

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
                            modifier = modifier.clickable { onPasswordVisibleChange(!passwordVisible) },
                            tint = colors.black
                        )
                    },
                    visualTransformation = if (passwordVisible) VisualTransformation.None else PasswordVisualTransformation()
                )

                SumoryTextField(
                    textState = checkPassword,
                    placeHolder = "비밀번호 확인",
                    onTextChange = onCheckPasswordChange,
                    isError = isPasswordError,
                    helperText = passwordErrorMessage,
                    icon = {
                        EyeIcon(
                            isSelected = checkPasswordVisible,
                            modifier = modifier.clickable {
                                onCheckPasswordVisibleChange(!checkPasswordVisible)
                            },
                            tint = colors.black
                        )
                    },
                    visualTransformation = if (checkPasswordVisible) VisualTransformation.None else PasswordVisualTransformation()
                )

                Spacer(modifier = modifier.height(15.dp))

                Button(
                    onClick = onSignUpClick,
                    modifier = modifier
                        .fillMaxWidth()
                        .height(48.dp),
                    shape = RoundedCornerShape(12.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = colors.darkPink)
                ) {
                    Text("가입하기", color = colors.white)
                }
            }
        }
    }
}

@DevicePreviews
@Composable
fun SignUpScreenPreview() {
    SignUpScreen(
        id = "",
        nickname = "",
        password = "",
        passwordVisible = false,
        checkPassword = "",
        checkPasswordVisible = false,
        onIdChange = {},
        onNicknameChange = {},
        onPasswordChange = {},
        onPasswordVisibleChange = {},
        onCheckPasswordChange = {},
        onCheckPasswordVisibleChange = {},
        onBackClick = {},
        onSignUpClick = {},
        isPasswordError = false
    )
}
