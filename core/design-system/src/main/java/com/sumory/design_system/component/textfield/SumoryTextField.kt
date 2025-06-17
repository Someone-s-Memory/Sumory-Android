package com.sumory.design_system.component.textfield

import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.sumory.design_system.icon.EyeIcon
import com.sumory.design_system.theme.SumoryTheme


@Composable
fun SumoryTextField(
    modifier: Modifier = Modifier,
    textState: String,
    placeHolder: String,
    helperText: String = "",
    isError: Boolean = false,
    visualTransformation: VisualTransformation = VisualTransformation.None,
    onTextChange: (String) -> Unit,
    icon: @Composable () -> Unit
) {
    SumoryTheme { colors, typography ->
        Column(modifier = modifier.fillMaxWidth()) {

            BasicTextField(
                modifier = Modifier
                    .fillMaxWidth()
                    .border(
                        width = 1.dp,
                        color = if (isError) colors.error else colors.gray100,
                        shape = RoundedCornerShape(size = 8.dp)
                    )
                    .padding(vertical = 14.dp, horizontal = 16.dp),
                value = textState,
                onValueChange = { newText -> onTextChange(newText) },
                visualTransformation = visualTransformation,
                singleLine = true,
                textStyle = typography.bodyRegular2.copy(color = colors.black),
                decorationBox = { innerTextField ->
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Box(modifier = Modifier.weight(1f)) {
                            if (textState.isEmpty()) {
                                Text(
                                    text = placeHolder,
                                    color = colors.gray400,
                                    style = typography.bodyRegular2
                                )
                            }
                            innerTextField()
                        }

                        icon()
                    }
                }
            )

            Text(
                text = helperText,
                color = colors.error,
                style = typography.bodyRegular2
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun SumoryTextFieldPreview() {
    val (textState, onTextChange) = remember { mutableStateOf("") }
    val (isSelected, setIsSelected) = remember { mutableStateOf(false)}

    Column(verticalArrangement = Arrangement.spacedBy(4.dp)) {
        SumoryTextField(
            textState = textState,
            placeHolder = "아이디를 입력해주세요",
            onTextChange = onTextChange,
            icon = {}
        )

        SumoryTextField(
            textState = textState,
            placeHolder = "비밀번호를 입력해주세요",
            onTextChange = onTextChange,
            icon = {
                EyeIcon(
                    isSelected = isSelected,
                    modifier = Modifier.clickable { setIsSelected(!isSelected)  },
                    tint = Color.Black
                )
            },
            visualTransformation = if(isSelected) VisualTransformation.None else PasswordVisualTransformation()
        )

        SumoryTextField(
            textState = textState,
            placeHolder = "비밀번호를 입력해주세요",
            isError = true,
            helperText = "아이디와 비밀번호가 일치하는지 확인해주세요",
            onTextChange = onTextChange,
            icon = {}
        )
    }
}