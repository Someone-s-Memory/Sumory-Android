package com.sumory.diary.view

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.sumory.design_system.icon.FilterIcon
import com.sumory.design_system.theme.SumoryTheme
import com.sumory.ui.DevicePreviews

@Composable
fun DiaryScreen(
    modifier: Modifier = Modifier,
){
    SumoryTheme { colors, typography ->
        Column(
            modifier
                .fillMaxSize()
                .background(colors.white)
                .padding(16.dp),
        ) {
            Row(
                modifier
                    .fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "모아보기",
                    style = typography.titleRegular3,
                    color = colors.black
                )
                Spacer(modifier.weight(1f))
                FilterIcon(
                    modifier.padding(start = 5.dp),
                    tint = colors.black
                )
            }
        }
    }
}

@DevicePreviews
@Composable
fun DiaryScreenPreview(){
    DiaryScreen()
}