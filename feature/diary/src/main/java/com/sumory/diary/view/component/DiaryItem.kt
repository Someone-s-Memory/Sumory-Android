package com.sumory.diary.view.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.sumory.design_system.theme.SumoryTheme
import com.sumory.ui.mapper.iconRes
import com.sumory.model.mapper.diary.toDiaryFeeling
import com.sumory.model.mapper.diary.toDiaryWeather
import com.sumory.model.model.diary.AllDiaryResponseModel
import com.sumory.ui.DevicePreviews

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DiaryItem(
    modifier: Modifier = Modifier,
    item: AllDiaryResponseModel,
    onClick: () -> Unit
) {
    SumoryTheme { colors, typography ->
        Card(
            onClick = onClick,
            shape = RoundedCornerShape(12.dp),
            elevation = CardDefaults.cardElevation(defaultElevation = 3.dp),
            colors = CardDefaults.cardColors(containerColor = colors.white),
            modifier = modifier.fillMaxWidth()
        ) {
            Column(modifier = modifier.padding(16.dp)) {
                Row(
                    modifier = modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(
                        text = item.title,
                        color = colors.black,
                        style = typography.bodyBold1
                    )

                    Row {
                        // 감정 아이콘
                        val feelingEnum = item.feeling.toDiaryFeeling()
                        Icon(
                            painter = painterResource(id = feelingEnum.iconRes()),
                            contentDescription = item.feeling,
                            modifier = Modifier.size(24.dp)
                        )
                        Spacer(modifier = modifier.width(4.dp))

                        // 날씨 아이콘
                        val weatherEnum = item.weather.toDiaryWeather()
                        Icon(
                            painter = painterResource(id = weatherEnum.iconRes()),
                            contentDescription = item.weather,
                            modifier = Modifier.size(24.dp)
                        )
                    }
                }

                Text(
                    text = item.date,
                    style = typography.captionRegular1,
                    color = colors.gray500,
                    modifier = modifier.padding(vertical = 4.dp)
                )

                // content는 제목 아래에 한 줄 정도 보여줌 (원래 UI에는 없었으니 선택적으로 추가)
                Text(
                    text = item.content,
                    style = typography.bodyRegular2,
                    color = colors.gray700,
                    maxLines = 1,
                    modifier = Modifier.padding(top = 4.dp)
                )
            }
        }
    }
}

@DevicePreviews
@Composable
private fun DiaryItemPreview() {
    DiaryItem(
        item = AllDiaryResponseModel(
            id = 1,
            title = "즐거운 하루",
            content = "오늘은 정말 행복했어요",
            feeling = "행복",
            weather = "맑음",
            date = "2025. 6. 10.",
            pictures = listOf("sample_image"),
            userID = "user1"
        ),
        onClick = {}
    )
}
