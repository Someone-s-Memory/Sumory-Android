package com.sumory.diary.view

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.asPaddingValues
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.systemBars
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.sumory.design_system.component.textfield.SumoryTextField
import com.sumory.design_system.icon.LeftArrowIcon
import com.sumory.design_system.icon.SaveIcon
import com.sumory.design_system.theme.SumoryTheme
import com.sumory.ui.DevicePreviews

@Composable
fun DiaryWriteScreen(
    modifier: Modifier = Modifier,
    date: String,
    title: String,
    onTitleChange: (String) -> Unit,
    content: String,
    onContentChange: (String) -> Unit,
    onBackClick: () -> Unit,
    selectedEmotion: String?,
    onEmotionSelected: (String) -> Unit,
    selectedWeather: String?,
    onWeatherSelected: (String) -> Unit,
    onSaveClick: () -> Unit
) {
    val emotionList = listOf("😊", "😢", "😳", "😠", "😆", "🤔")
    val weatherList = listOf("🌞", "☁️", "🌧️", "❄️", "🌩️", "🌈")

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
                    modifier = modifier.clickable { onBackClick() },
                    tint = colors.black
                )
                Spacer(modifier = modifier.weight(1f))
                Text(
                    text = date,
                    style = typography.titleBold3,
                    color = colors.black
                )
                Spacer(modifier = modifier.weight(1f))
                Box(
                    modifier = modifier
                        .size(24.dp)
                        .clip(RoundedCornerShape(5.dp))
                        .background(colors.darkPink)
                        .clickable { onSaveClick() },
                    contentAlignment = Alignment.Center
                ) {
                    SaveIcon(
                        modifier = modifier
                            .size(18.dp),
                        tint = colors.white
                    )
                }
            }

            Box(
                modifier = modifier
                    .padding(top = 30.dp)
            ) {
                SumoryTextField(
                    textState = title,
                    placeHolder = "제목을 입력하세요",
                    focusText = "제목을 입력하세요",
                    onTextChange = onTitleChange,
                    icon = {}
                )
            }

            Text(
                text = "감정",
                style = typography.bodyBold1,
                color = colors.black
            )
            Spacer(modifier = modifier.height(8.dp))
            Row(horizontalArrangement = Arrangement.spacedBy(12.dp)) {
                emotionList.forEach { emoji ->
                    Box(
                        modifier = modifier
                            .size(48.dp)
                            .clip(RoundedCornerShape(12.dp))
                            .background(
                                if (selectedEmotion == emoji) colors.pinkSoftBackground else colors.white
                            )
                            .border(
                                1.dp,
                                if (selectedEmotion == emoji) colors.main else colors.white,
                                RoundedCornerShape(12.dp)
                            )
                            .clickable { onEmotionSelected(emoji) },
                        contentAlignment = Alignment.Center
                    ) {
                        Text(text = emoji, fontSize = 24.sp)
                    }
                }
            }

            Spacer(modifier = modifier.height(24.dp))

            Text(
                text = "날씨",
                style = typography.bodyBold1,
                color = colors.black
            )
            Spacer(modifier = modifier.height(8.dp))
            Row(horizontalArrangement = Arrangement.spacedBy(12.dp)) {
                weatherList.forEach { emoji ->
                    Box(
                        modifier = modifier
                            .size(48.dp)
                            .clip(RoundedCornerShape(12.dp))
                            .background(
                                if (selectedWeather == emoji) colors.pinkSoftBackground else colors.white
                            )
                            .border(
                                1.dp,
                                if (selectedWeather == emoji) colors.main else colors.white,
                                RoundedCornerShape(12.dp)
                            )
                            .clickable { onWeatherSelected(emoji) },
                        contentAlignment = Alignment.Center
                    ) {
                        Text(text = emoji, fontSize = 24.sp)
                    }
                }
            }

            Spacer(modifier = modifier.height(24.dp))

            Box(
                modifier = modifier
                    .fillMaxWidth()
                    .height(140.dp)
                    .clip(RoundedCornerShape(12.dp))
                    .border(1.dp, colors.main, RoundedCornerShape(12.dp))
                    .clickable { /* TODO: 이미지 추가 기능 */ },
                contentAlignment = Alignment.Center
            ) {
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Text(
                        text = "사진을 추가해보세요",
                        color = colors.main,
                        style = typography.bodyRegular2
                    )
                }
            }

            Spacer(modifier = modifier.height(24.dp))

            SumoryTextField(
                modifier = modifier
                    .fillMaxHeight(),
                textState = content,
                placeHolder = "오늘 하루는 어땠나요?",
                focusText = "오늘 하루는 어땠나요?",
                singleLine = false,
                onTextChange = onContentChange,
                icon = {}
            )

            Spacer(modifier = modifier.height(32.dp))
        }
    }
}

@DevicePreviews
@Composable
private fun DiaryWriteScreenPreview() {
    DiaryWriteScreen(
        date = "2025년 7월 10일 수요일",
        title = "",
        content = "",
        onTitleChange = {},
        onContentChange = {},
        onBackClick = {},
        selectedEmotion = null,
        onEmotionSelected = {},
        selectedWeather = null,
        onWeatherSelected = {},
        onSaveClick = {}
    )
}
