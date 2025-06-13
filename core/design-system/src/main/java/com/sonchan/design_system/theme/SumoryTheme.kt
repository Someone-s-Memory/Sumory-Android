package com.sumory.design_system.theme

import androidx.compose.runtime.Composable
import com.sumory.design_system.theme.color.ColorTheme
import com.sumory.design_system.theme.color.SumoryColor

@Composable
fun SumoryTheme(
    colors: SumoryColor = SumoryColor,
    typography: SumoryTypography = SumoryTypography,
    content: @Composable (colors: ColorTheme, typography: SumoryTypography) -> Unit
){
    content(colors, typography)
}