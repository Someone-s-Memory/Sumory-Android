package com.sumory.stat.view.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.sumory.design_system.theme.SumoryTheme
import com.sumory.model.mapper.diary.toDiaryFeeling
import com.sumory.ui.DevicePreviews
import com.sumory.ui.mapper.iconRes

@Composable
fun EmotionStatCard(
    title: String,
    feelings: Map<String, Int>,
    total: Int
) {
    SumoryTheme { colors, typography ->
        Card(
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(16.dp),
            elevation = CardDefaults.cardElevation(4.dp),
            colors = CardDefaults.cardColors(containerColor = colors.white)
        ) {
            Column(Modifier.padding(16.dp)) {
                Text(
                    text = title,
                    style = typography.bodyBold1,
                    color = colors.black
                )
                Spacer(Modifier.height(16.dp))

                feelings.forEach { (feeling, count) ->
                    EmotionBar(feeling = feeling, count = count, total = total)
                    Spacer(Modifier.height(12.dp))
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

@DevicePreviews
@Composable
fun EmotionStatCardPreview() {
    EmotionStatCard(
        title = "7월의 감정 분포",
        feelings = mapOf("행복" to 4, "슬픔" to 2, "화남" to 1),
        total = 7
    )
}