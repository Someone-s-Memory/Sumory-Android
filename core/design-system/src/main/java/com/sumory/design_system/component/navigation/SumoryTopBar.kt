package com.sumory.design_system.component.navigation

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.sumory.design_system.icon.SettingIcon
import com.sumory.design_system.theme.SumoryTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SumoryTopBar(
    modifier: Modifier = Modifier,
    title: String,
    coinCount: Int,
    currentRoute: String,
    onNavigateTo: (String) -> Unit,
) {
    SumoryTheme { colors, typography ->
        Column {
            TopAppBar(
                title = {
                    Text(text = title, style = typography.titleRegular2)
                },
                actions = {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Text(
                            text = "💰 $coinCount",
                            modifier = modifier
                                .padding(start = 4.dp, end = 8.dp)
                                .clickable { onNavigateTo("storeRoute") },
                            style = typography.bodyRegular1
                        )
                        IconButton(onClick = { onNavigateTo("settingRoute") }) {
                            SettingIcon(tint = colors.black)
                        }
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = colors.white
                )
            )
            Divider(
                color = colors.gray100,
                thickness = 1.dp
            )
        }
    }
}


@Preview
@Composable
private fun SumoryTopBarPreview() {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
    ) {
        SumoryTopBar(
            title = "Sumory",
            coinCount = 150,
            currentRoute = "",
            onNavigateTo = {  },
        )
    }
}