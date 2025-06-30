package com.sumory.diary.view

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.asPaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.systemBars
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.sumory.design_system.component.textfield.SumoryTextField
import com.sumory.design_system.icon.LeftArrowIcon
import com.sumory.design_system.theme.SumoryTheme
import com.sumory.ui.DevicePreviews

@Composable
fun DiaryWriteScreen(
    modifier: Modifier = Modifier,
    date: String,
    title: String,
    onTitleChange: (String) -> Unit,
    onBackClick: () -> Unit
){
    SumoryTheme { colors, typography ->
        Column(
            modifier = modifier
                .fillMaxSize()
                .background(colors.white)
                .padding(16.dp)
                .padding(WindowInsets.systemBars.asPaddingValues())
        ) {
            Row(
                modifier = modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                LeftArrowIcon(
                    modifier = modifier
                        .clickable { onBackClick() },
                    tint = colors.black
                )
                Spacer(modifier.weight(1f))
                Text(
                    text = date,
                    style = typography.titleBold3,
                    color = colors.black
                )
                Spacer(modifier.weight(1f))
                Spacer(modifier.width(24.dp))
            }

            SumoryTextField(
                modifier = modifier
                    .padding(horizontal = 10.dp, vertical = 30.dp),
                textState = title,
                placeHolder = "제목을 입력하세요",
                focusText = "제목을 입력하세요",
                onTextChange = onTitleChange,
                icon = {}
            )
        }
    }
}

@DevicePreviews
@Composable
private fun DiaryWriteScreenPreview(){
    DiaryWriteScreen(
        date = "2025년 7월 10일 수요일",
        title = "",
        onTitleChange = {},
        onBackClick = {}
    )
}