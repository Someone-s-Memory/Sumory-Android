package com.sumory.diary

import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.sumory.diary.view.DiaryScreen

const val diaryRoute = "diaryRoute"

fun NavGraphBuilder.diaryScreen() {
    composable(diaryRoute) {
        DiaryScreen()
    }
}