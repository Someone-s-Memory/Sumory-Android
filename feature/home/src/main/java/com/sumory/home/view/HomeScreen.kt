package com.sumory.home.view

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import com.sumory.design_system.theme.SumoryTheme
import com.sumory.ui.DevicePreviews

@Composable
fun HomeScreen(){
    SumoryTheme { colors, typography ->
        Text("홈 화면")
    }
}

@DevicePreviews
@Composable
fun HomeScreenPreview(){
    HomeScreen()
}