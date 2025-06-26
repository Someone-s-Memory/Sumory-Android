package com.sumory.calendar.view.component

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
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.sumory.design_system.theme.SumoryTheme
import com.sumory.model.entity.calendar.CalendarDiaryListEntity
import com.sumory.ui.DevicePreviews

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CalendarDiaryItem(
    modifier: Modifier = Modifier,
    item: CalendarDiaryListEntity,
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
                        Text(
                            text = item.emotionEmoji,
                            style = typography.bodyBold1,
                        )
                        Spacer(modifier = modifier.width(4.dp))
                        Text(
                            text = item.weatherEmoji,
                            style = typography.bodyBold1,
                        )
                    }
                }

                Spacer(modifier.height(10.dp))

                Text(
                    text = item.content,
                    style = typography.captionRegular1,
                    color = colors.black,
                    modifier = modifier.padding(vertical = 4.dp),
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis // 넘치면 ... 처리
                )
            }
        }
    }
}

@DevicePreviews
@Composable
private fun CalendarDiaryItemPreview(){
    CalendarDiaryItem(
        item = CalendarDiaryListEntity(
            id = 1,
            title = "즐거운 하루",
            content = "너무 즐거워서 집에만 있었다 \n 동해물과",
            emotionEmoji = "😊",
            weatherEmoji = "☀️"),
        onClick = {},
    )
}