package com.sumory.diary.view

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.asPaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.sizeIn
import androidx.compose.foundation.layout.systemBars
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
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
    var zoomedImageUrl by remember { mutableStateOf<String?>(null) }

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
                    style = typography.titleRegular3,
                    color = colors.black
                )
                Spacer(modifier.weight(1f))
                EditIcon(
                    modifier = modifier
                        .clickable { onEditClick() },
                    tint = colors.black
                )
            }

            Spacer(modifier.height(16.dp))

            Row(
                modifier = modifier
                    .fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(12.dp)) {
                Button(onClick = {}, shape = RoundedCornerShape(50), colors = ButtonDefaults.buttonColors(colors.gray100)) {
                    Text(text = "$emotion 오늘의 기분", color = colors.black)
                }
                Button(onClick = {}, shape = RoundedCornerShape(50), colors = ButtonDefaults.buttonColors(colors.gray100)) {
                    Text(text = "$weather 날씨", color = colors.black)
                }
            }

            Spacer(modifier.height(24.dp))

            Text(text = title, style = typography.titleBold2, color = colors.black)
            Divider(modifier = modifier.padding(vertical = 12.dp), thickness = 1.dp, color = colors.gray200)

            if (photoUrls.isNotEmpty()) {
                LazyRow(horizontalArrangement = Arrangement.spacedBy(12.dp)) {
                    items(photoUrls) { url ->
                        Image(
                            painter = rememberAsyncImagePainter(url),
                            contentDescription = "Diary photo",
                            modifier = modifier
                                .size(120.dp)
                                .clip(RoundedCornerShape(8.dp))
                                .clickable { zoomedImageUrl = url },
                            contentScale = ContentScale.Crop,
                        )
                    }
                }
            }

            Text(text = content, style = typography.bodyRegular2, color = colors.black)

            zoomedImageUrl?.let { url ->
                Dialog(onDismissRequest = { zoomedImageUrl = null }) {
                    Box(
                        modifier = modifier
                            .fillMaxSize()
                            .background(colors.black.copy(alpha = 0.8f))
                            .pointerInput(Unit) {
                                detectTapGestures(onTap = { zoomedImageUrl = null }) // 배경 클릭 시 닫기
                            },
                        contentAlignment = Alignment.Center
                    ) {
                        Image(
                            painter = rememberAsyncImagePainter(url),
                            contentDescription = "Zoomed Image",
                            modifier = modifier
                                .fillMaxSize()  // 화면 꽉 채우기
                                .clip(RoundedCornerShape(12.dp))
                                .pointerInput(Unit) {
                                    // 이미지 터치는 닫기 이벤트 막기 용
                                },
                            contentScale = ContentScale.Fit  // 비율 유지하면서 화면 안에 꽉 차게
                        )
                    }
                }
            }
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
