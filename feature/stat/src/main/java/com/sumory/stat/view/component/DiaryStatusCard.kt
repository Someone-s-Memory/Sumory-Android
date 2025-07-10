package com.sumory.stat.view.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.sumory.design_system.theme.SumoryTheme
import com.sumory.ui.DevicePreviews

@Composable
fun DiaryStatusCard(total: Int, sequence: Int) {
    SumoryTheme { colors, typography ->
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
fun DiaryStatusCardPreview() {
    DiaryStatusCard(total = 20, sequence = 7)
}