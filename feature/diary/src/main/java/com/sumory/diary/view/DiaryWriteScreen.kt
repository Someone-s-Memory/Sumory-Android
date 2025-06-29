package com.sumory.diary.view

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
fun DiaryWriteScreen(){
    SumoryTheme { colors, typography ->
        Box(
            Modifier
                .fillMaxSize()
                .background(colors.white),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = "일기 작성 화면",
                color = colors.black
            )
        }
    }
}

@DevicePreviews
@Composable
private fun DiaryWriteScreenPreview(){
    DiaryWriteScreen()
}