package com.sumory.diary.viewmodel.mapper

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

fun String.toDiaryFeeling(): DiaryFeeling = when(this) {
    "행복" -> DiaryFeeling.HAPPY
    "슬픔" -> DiaryFeeling.SAD
    "화남" -> DiaryFeeling.ANGRY
    "즐거움" -> DiaryFeeling.FUN
    "생각중" -> DiaryFeeling.THINKING
    else -> DiaryFeeling.THINKING
}