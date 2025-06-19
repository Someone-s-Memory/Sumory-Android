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

    val userId by viewModel.userId.collectAsState()
    val nickname by viewModel.nickname.collectAsState()
    val password by viewModel.password.collectAsState()
    val passwordVisible by viewModel.passwordVisible.collectAsState()
    val checkPassword by viewModel.checkPassword.collectAsState()
    val checkPasswordVisible by viewModel.checkPasswordVisible.collectAsState()

    val isPasswordError = password != checkPassword && checkPassword.isNotEmpty()
    val passwordErrorMessage = if (isPasswordError) "비밀번호가 일치하는지 확인해주세요" else ""

    val isButtonEnabled = userId.isNotBlank() && nickname.isNotBlank()
            && password.isNotBlank() && checkPassword.isNotBlank() && !isPasswordError

    val isError = signUpState is SignUpUiState.Error
    val errorMessage = if (isError) "동일한 아이디가 존재합니다." else ""

    LaunchedEffect(signUpState) {
        if (signUpState is SignUpUiState.Success) {
            onSignUpSuccess()
        }
    }

    SignUpScreen(
        userId = userId,
        nickname = nickname,
        password = password,
        passwordVisible = passwordVisible,
        checkPassword = checkPassword,
        checkPasswordVisible = checkPasswordVisible,
        onUserIdChange = {
            viewModel.onUserIdChange(it)
            viewModel.resetError()
        },
        onNicknameChange = {
            viewModel.onNicknameChange(it)
            viewModel.resetError()
        },
        onPasswordChange = {
            viewModel.onPasswordChange(it)
            viewModel.resetError()
        },
        onPasswordVisibleChange = { viewModel.onPasswordVisibleChange(it) },
        onCheckPasswordChange = {
            viewModel.onCheckPasswordChange(it)
            viewModel.resetError()
        },
        onCheckPasswordVisibleChange = { viewModel.onCheckPasswordVisibleChange(it) },
        onBackClick = onBackClick,
        onSignUpClick = {
            if (!isPasswordError) {
                viewModel.signUp(
                    userId = userId,
                    password = password,
                    passwordCheck = checkPassword,
                    nickname = nickname
                )
            }
        },
        isPasswordError = isPasswordError,
        passwordErrorMessage = passwordErrorMessage,
        isError = isError,
        errorMessage = errorMessage,
        isButtonEnabled = isButtonEnabled
    )
}

@Composable
fun SignUpScreen(
    modifier: Modifier = Modifier,
    userId: String,
    nickname: String,
    password: String,
    passwordVisible: Boolean,
    checkPassword: String,
    checkPasswordVisible: Boolean,
    onUserIdChange: (String) -> Unit,
    onNicknameChange: (String) -> Unit,
    onPasswordChange: (String) -> Unit,
    onPasswordVisibleChange: (Boolean) -> Unit,
    onCheckPasswordChange: (String) -> Unit,
    onCheckPasswordVisibleChange: (Boolean) -> Unit,
    onBackClick: () -> Unit,
    onSignUpClick: () -> Unit,
    isPasswordError: Boolean,
    passwordErrorMessage: String = "",
    isError: Boolean,
    errorMessage: String,
    isButtonEnabled: Boolean
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
                    icon = {},
                    isError = isError
                )

                SumoryTextField(
                    textState = userId,
                    placeHolder = "아이디",
                    onTextChange = onUserIdChange,
                    icon = {},
                    isError = isError
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
                    visualTransformation = if (passwordVisible) VisualTransformation.None else PasswordVisualTransformation(),
                    isError = isError
                )

                SumoryTextField(
                    textState = checkPassword,
                    placeHolder = "비밀번호 확인",
                    onTextChange = onCheckPasswordChange,
                    isError = isPasswordError || isError,
                    helperText = when {
                        isPasswordError -> passwordErrorMessage // "비밀번호가 일치하는지 확인해주세요"
                        isError -> errorMessage                 // "동일한 아이디가 존재합니다"
                        else -> ""
                    },
                    icon = {
                        EyeIcon(
                            isSelected = checkPasswordVisible,
                            modifier = modifier.clickable {
                                onCheckPasswordVisibleChange(!checkPasswordVisible)
                            },
                            tint = colors.black
                        )
                    },
                    visualTransformation = if (checkPasswordVisible) VisualTransformation.None else PasswordVisualTransformation(),
                )

                Spacer(modifier = modifier.height(15.dp))

                Button(
                    onClick = onSignUpClick,
                    enabled = isButtonEnabled,
                    modifier = modifier
                        .fillMaxWidth()
                        .height(48.dp),
                    shape = RoundedCornerShape(12.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = if (isButtonEnabled) colors.darkPink else colors.main
                    )
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
        userId = "",
        nickname = "",
        password = "",
        passwordVisible = false,
        checkPassword = "",
        checkPasswordVisible = false,
        onUserIdChange = {},
        onNicknameChange = {},
        onPasswordChange = {},
        onPasswordVisibleChange = {},
        onCheckPasswordChange = {},
        onCheckPasswordVisibleChange = {},
        onBackClick = {},
        onSignUpClick = {},
        isPasswordError = false,
        isButtonEnabled = false,
        passwordErrorMessage = "",
        isError = false,
        errorMessage = ""
    )
}
