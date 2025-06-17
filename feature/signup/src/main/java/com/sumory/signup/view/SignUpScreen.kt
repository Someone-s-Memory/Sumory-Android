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
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import com.sumory.design_system.component.textfield.SumoryTextField
import com.sumory.design_system.icon.EyeIcon
import com.sumory.design_system.icon.LeftArrowIcon
import com.sumory.design_system.theme.SumoryTheme
import com.sumory.ui.DevicePreviews

@Composable
fun SignUpScreen(
    modifier: Modifier = Modifier,
    onBackClick: () -> Unit
){
    var id by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var passwordVisible by remember { mutableStateOf(false) }
    var checkPassword by remember { mutableStateOf("") }
    var checkPasswordVisible by remember { mutableStateOf(false) }

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
                    textState = id,
                    placeHolder = "아이디",
                    onTextChange = { id = it },
                    icon = {}
                )

                SumoryTextField(
                    textState = email,
                    placeHolder = "이메일",
                    onTextChange = { email = it },
                    icon = {}
                )

                SumoryTextField(
                    textState = password,
                    placeHolder = "비밀번호",
                    onTextChange = { password = it },
                    icon = {
                        EyeIcon(
                            isSelected = passwordVisible,
                            modifier = modifier.clickable { passwordVisible = !passwordVisible },
                            tint = colors.black
                        )
                    },
                    visualTransformation = if (passwordVisible) VisualTransformation.None else PasswordVisualTransformation()
                )

                SumoryTextField(
                    textState = checkPassword,
                    placeHolder = "비밀번호 확인",
                    onTextChange = { checkPassword = it },
                    isError = password != checkPassword,
                    helperText = if(password != checkPassword) "비밀번호가 일치하는지 확인해주세요" else "",
                    icon = {
                        EyeIcon(
                            isSelected = checkPasswordVisible,
                            modifier = modifier.clickable {
                                checkPasswordVisible = !checkPasswordVisible
                            },
                            tint = colors.black
                        )
                    },
                    visualTransformation = if (checkPasswordVisible) VisualTransformation.None else PasswordVisualTransformation()
                )

                Spacer(modifier = modifier.height(15.dp))

                Button(
                    onClick = { /* TODO: 회원가입 처리 */ },
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
fun SignUpScreenPreview(){
    SignUpScreen(
        onBackClick = {}
    )
}