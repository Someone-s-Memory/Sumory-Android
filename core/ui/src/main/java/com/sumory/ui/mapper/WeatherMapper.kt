package com.sumory.ui.mapper

import androidx.annotation.DrawableRes
import com.sumory.design_system.R
import com.sumory.model.type.DiaryWeather

@DrawableRes
fun DiaryWeather.iconRes(): Int = when (this) {
    DiaryWeather.SUNNY -> R.drawable.ic_coin
    DiaryWeather.CLOUDY ->  R.drawable.ic_coin
    DiaryWeather.RAINY ->  R.drawable.ic_coin
    DiaryWeather.SNOWY ->  R.drawable.ic_coin
    DiaryWeather.THUNDER ->  R.drawable.ic_coin
}