package com.sumory.store.view

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.sumory.design_system.theme.SumoryTheme
import com.sumory.ui.DevicePreviews

@Composable
fun StoreScreen(){
    SumoryTheme { colors, _ ->
        Box(
            Modifier
            .fillMaxSize()
            .background(colors.gray50),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = "상점 화면",
                color = colors.black
            )
        }
    }
}

@DevicePreviews
@Composable
private fun StoreScreenPreview(){
    StoreScreen()
}