package com.sumory.setting.view.component

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
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.sumory.design_system.icon.RightArrowIcon
import com.sumory.design_system.icon.SettingIcon
import com.sumory.design_system.theme.SumoryTheme
import com.sumory.ui.DevicePreviews


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SettingItem(
    modifier: Modifier = Modifier,
    title: String,
    subtitle: String,
    onClick: () -> Unit
) {
    SumoryTheme { colors, typography ->
        Card(
            onClick = onClick,
            shape = RoundedCornerShape(12.dp),
            elevation = CardDefaults.cardElevation(defaultElevation = 3.dp),
            colors = CardDefaults.cardColors(containerColor = colors.white),
            modifier = modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp, vertical = 8.dp)
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column(
                    modifier = Modifier.weight(1f)
                ) {
                    Text(
                        text = title,
                        color = colors.black,
                        style = MaterialTheme.typography.titleMedium
                    )
                    Text(
                        text = subtitle,
                        style = typography.captionRegular1,
                        color = colors.gray500,
                        modifier = Modifier.padding(top = 4.dp)
                    )
                }

                RightArrowIcon(
                    tint = colors.gray400
                )
            }
        }
    }
}

@DevicePreviews
@Composable
fun SettingItemPreview(){
    SettingItem(
        title = "로그아웃",
        subtitle = "계정에서 로그아웃",
        onClick = {},
    )
}