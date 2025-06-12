package com.sonchan.design_system.theme

import androidx.compose.runtime.Composable
import com.sonchan.design_system.theme.color.ColorTheme
import com.sonchan.design_system.theme.color.SumoryColor

@Composable
fun SumoryTheme(
    colors: SumoryColor = SumoryColor,
    typography: SumoryTypography = SumoryTypography,
    content: @Composable (colors: ColorTheme, typography: SumoryTypography) -> Unit
){
    content(colors, typography)
}