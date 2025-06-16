package com.sumory.home.view

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.sumory.design_system.theme.SumoryTheme
import com.sumory.ui.DevicePreviews

@Composable
fun HomeScreen(
    modifier: Modifier = Modifier,
    petName: String,
    affinity: Int,
){
    SumoryTheme { colors, typography ->
        Column(
            modifier.fillMaxSize()
                .background(color = colors.white),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = petName,
                style = typography.titleBold2,
                color = colors.black,
            )
            Row {
                Text(
                    text = "❤️ $affinity",
                    style = typography.captionBold1,
                    color = colors.black,
                )
            }
        }
    }
}

@DevicePreviews
@Composable
fun HomeScreenPreview(){
    HomeScreen(
        petName = "나의 펫",
        affinity = 15
    )
}