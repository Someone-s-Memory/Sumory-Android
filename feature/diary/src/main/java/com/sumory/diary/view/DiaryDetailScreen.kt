package com.sumory.diary.view

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.sumory.design_system.theme.SumoryTheme
import com.sumory.ui.DevicePreviews

@Composable
fun DiaryDetailScreen(
    modifier: Modifier = Modifier,
    date: String,
    emotion: String,
    weather: String,
    title: String,
    content: String,
    onEditClick: () -> Unit,
) {
    SumoryTheme { colors, typography ->
        Column(
            modifier = modifier
                .fillMaxSize()
                .background(colors.white)
                .padding(16.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = date,
                    style = typography.titleBold2,
                    color = colors.black
                )
                Spacer(Modifier.weight(1f))
                IconButton(onClick = onEditClick) {
                    Icon(Icons.Default.Edit, contentDescription = "Edit", tint = colors.black)
                }
            }

            Spacer(Modifier.height(16.dp))

            Row(horizontalArrangement = Arrangement.spacedBy(12.dp)) {
                Button(onClick = {}, shape = RoundedCornerShape(50), colors = ButtonDefaults.buttonColors(colors.gray100)) {
                    Text(text = "$emotion 오늘의 기분", color = colors.black)
                }
                Button(onClick = {}, shape = RoundedCornerShape(50), colors = ButtonDefaults.buttonColors(colors.gray100)) {
                    Text(text = "$weather 날씨", color = colors.black)
                }
            }

            Spacer(Modifier.height(24.dp))

            Text(text = title, style = typography.titleBold2, color = colors.black)
            Divider(modifier = Modifier.padding(vertical = 12.dp), thickness = 1.dp, color = colors.gray200)
            Text(text = content, style = typography.bodyRegular2, color = colors.black)
        }
    }
}

@DevicePreviews
@Composable
fun DiaryDetailScreenPreview() {
    DiaryDetailScreen(
        date = "2025년 6월 10일 화요일",
        emotion = "😊",
        weather = "☀️",
        title = "즐거운 하루",
        content = "오늘은 정말 좋은 하루였다!",
        onEditClick = {}
    )
}
