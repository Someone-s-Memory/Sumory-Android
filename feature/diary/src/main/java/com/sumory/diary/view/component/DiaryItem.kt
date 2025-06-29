package com.sumory.diary.view.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.sumory.design_system.theme.SumoryTheme
import com.sumory.model.entity.diary.DiaryListEntity
import com.sumory.ui.DevicePreviews

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DiaryItem(
    modifier: Modifier = Modifier,
    item: DiaryListEntity,
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

                Text(
                    text = item.date,
                    style = typography.captionRegular1,
                    color = colors.gray500,
                    modifier = modifier.padding(vertical = 4.dp)
                )
            }
        }
    }
}

@DevicePreviews
@Composable
private fun DiaryItemPreview(){
    DiaryItem(
        item = DiaryListEntity(
            id = 1,
            title = "즐거운 하루",
            date = "2025. 6. 10.",
            emotionEmoji = "😊",
            weatherEmoji = "☀️"),
        onClick = {},
    )
}