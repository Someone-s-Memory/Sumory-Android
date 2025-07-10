package com.sumory.ui.mapper

import androidx.annotation.DrawableRes
import com.sumory.design_system.R
import com.sumory.model.type.DiaryFeeling

@DrawableRes
fun DiaryFeeling.iconRes(): Int = when (this) {
    DiaryFeeling.HAPPY -> R.drawable.ic_coin
    DiaryFeeling.SAD -> R.drawable.ic_coin
    DiaryFeeling.ANGRY -> R.drawable.ic_coin
    DiaryFeeling.FUN -> R.drawable.ic_coin
    DiaryFeeling.THINKING -> R.drawable.ic_coin
}