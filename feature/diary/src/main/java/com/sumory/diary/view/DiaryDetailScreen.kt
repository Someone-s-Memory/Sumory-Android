package com.sumory.diary.view

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import coil.compose.rememberAsyncImagePainter
import com.sumory.design_system.icon.EditIcon
import com.sumory.design_system.icon.LeftArrowIcon
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
    photoUrls: List<String> = emptyList(),
    onEditClick: () -> Unit,
    onBackClick: () -> Unit
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
                LeftArrowIcon(
                    modifier = modifier
                        .clickable { onBackClick() },
                    tint = colors.black
                )
                Spacer(Modifier.weight(1f))
                Text(
                    text = date,
                    style = typography.titleRegular3,
                    color = colors.black
                )
                Spacer(Modifier.weight(1f))
                EditIcon(
                    modifier = modifier
                        .clickable { onEditClick() },
                    tint = colors.black
                )
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
            if (photoUrls.isNotEmpty()) {
                LazyRow(horizontalArrangement = Arrangement.spacedBy(12.dp)) {
                    items(photoUrls) { url ->
                        Image(
                            painter = rememberAsyncImagePainter(url),
                            contentDescription = "Diary photo",
                            modifier = modifier
                                .size(120.dp)
                                .clip(RoundedCornerShape(8.dp)),
                            contentScale = ContentScale.Crop,
                        )
                    }
                }
            }
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
        photoUrls = listOf(
            "https://picsum.photos/200/300",
            "https://picsum.photos/201/300",
            "https://picsum.photos/202/300"
        ),
        onEditClick = {},
        onBackClick = {}
    )
}
