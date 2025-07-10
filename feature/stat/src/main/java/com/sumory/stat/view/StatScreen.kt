package com.sumory.stat.view

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.sumory.design_system.theme.SumoryTheme
import com.sumory.model.mapper.diary.toDiaryFeeling
import com.sumory.ui.DevicePreviews
import com.sumory.ui.mapper.iconRes

@Composable
fun StatScreen(
    feelings: Map<String, Int>,
    total: Int,
    sequence: Int
) {
    SumoryTheme { colors, typography ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(colors.gray50)
                .padding(horizontal = 16.dp)
        ) {
            Spacer(Modifier.height(16.dp))

            // Title
            Text(
                text = "감정 통계",
                color = colors.black,
                style = typography.titleMedium1
            )

            Spacer(Modifier.height(24.dp))

            // 감정 분포 카드
            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(16.dp),
                elevation = CardDefaults.cardElevation(4.dp),
                colors = CardDefaults.cardColors(containerColor = colors.white)
            ) {
                Column(Modifier.padding(16.dp)) {
                    Text(
                        text = "이번 달 감정 분포",
                        color = colors.black,
                        style = typography.bodyBold1
                    )

                    Spacer(Modifier.height(16.dp))

                    feelings.forEach { (feeling, count) ->
                        EmotionBar(feeling = feeling, count = count, total = total)
                        Spacer(Modifier.height(12.dp))
                    }
                }
            }

            Spacer(Modifier.height(24.dp))

            // 일기 작성 현황 카드
            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(16.dp),
                elevation = CardDefaults.cardElevation(4.dp),
                colors = CardDefaults.cardColors(containerColor = colors.white)
            ) {
                Row(
                    modifier = Modifier
                        .padding(vertical = 24.dp)
                        .fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceEvenly,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    StatisticItem(title = "총 일기 수", value = total)
                    StatisticItem(title = "연속 작성일", value = sequence)
                }
            }
        }
    }
}

@Composable
fun EmotionBar(feeling: String, count: Int, total: Int) {
    val ratio = if (total > 0) count.toFloat() / total else 0f

    SumoryTheme { colors, typography ->
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween,
            modifier = Modifier.fillMaxWidth()
        ) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Icon(
                    painter = painterResource(id = feeling.toDiaryFeeling().iconRes()),
                    contentDescription = feeling,
                    tint = colors.black,
                )
                Spacer(Modifier.width(8.dp))
                Text(
                    text = feeling,
                    color = colors.black,
                    style = typography.bodyRegular2
                )
            }

            Row(verticalAlignment = Alignment.CenterVertically) {
                LinearProgressIndicator(
                    progress = ratio,
                    modifier = Modifier
                        .width(100.dp)
                        .height(8.dp)
                        .clip(RoundedCornerShape(4.dp)),
                    color = colors.darkPink,
                    trackColor = colors.pinkSoftBackground
                )
                Spacer(Modifier.width(8.dp))
                Text(
                    text = "${count}일",
                    color = colors.black,
                    style = typography.captionRegular1
                )
            }
        }
    }
}

@Composable
fun StatisticItem(title: String, value: Int) {
    SumoryTheme { colors, typography ->
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Text(
                text = value.toString(),
                color = colors.darkPink,
                style = typography.titleBold2
            )
            Text(
                text = title,
                color = colors.black,
                style = typography.captionRegular1
            )
        }
    }
}

@DevicePreviews
@Composable
fun StatScreenPreview() {
    val mockFeelings = mapOf(
        "행복" to 4,
        "슬픔" to 3,
        "나쁘지 않음" to 3,
        "화남" to 1
    )

    StatScreen(
        feelings = mockFeelings,
        total = 11,
        sequence = 3
    )
}