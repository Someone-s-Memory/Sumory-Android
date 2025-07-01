package com.sumory.model.mapper.diary

import com.sumory.model.type.DiaryFeeling
import com.sumory.model.type.DiaryWeather

fun String.toDiaryFeeling(): DiaryFeeling = when(this) {
    "행복" -> DiaryFeeling.HAPPY
    "슬픔" -> DiaryFeeling.SAD
    "화남" -> DiaryFeeling.ANGRY
    "즐거움" -> DiaryFeeling.FUN
    "생각중" -> DiaryFeeling.THINKING
    else -> DiaryFeeling.THINKING
}

fun String.toDiaryWeather(): DiaryWeather = when(this) {
    "맑음" -> DiaryWeather.SUNNY
    "구름" -> DiaryWeather.CLOUDY
    "비" -> DiaryWeather.RAINY
    "눈" -> DiaryWeather.SNOWY
    else -> DiaryWeather.CLOUDY
}
