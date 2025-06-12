package com.sonchan.design_system.theme.color

import androidx.compose.ui.graphics.Color

object SumoryColor : ColorTheme() {

    // 메인 색상
    override val main = Color(0xFFFFB3C6) // 포인트 핑크

    override val error = Color(0xFFDF454A)
    override val success = Color(0xFF4CAF50)

    override val gray800 = Color(0xFF222222) // 메인 텍스트
    override val gray700 = Color(0xFF333333)
    override val gray600 = Color(0xFF666666) // 서브 텍스트
    override val gray500 = Color(0xFF999999)
    override val gray400 = Color(0xFFBDBDBD) // 입력 힌트 텍스트
    override val gray300 = Color(0xFFD9D9D9)
    override val gray200 = Color(0xFFE5E6EB) // Divider, Border
    override val gray100 = Color(0xFFF4F4F5) // 달력 배경 등
    override val gray50 = Color(0xFFFDFDFD)

    override val pinkSoftBackground = Color(0xFFFFF5F8) // 감정/날씨 아이콘 배경, 텍스트필드

    // 기본
    override val black = Color(0xFF121212)
    override val white = Color(0xFFFFFFFF)
}
