package com.sumory.diary

import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.sumory.diary.view.DiaryScreen
import com.sumory.model.entity.diary.DiaryListEntity

const val diaryRoute = "diaryRoute"

fun NavGraphBuilder.diaryScreen() {
    composable(diaryRoute) {
        val dummyList = listOf(
            DiaryListEntity(1,"즐거운 하루", "2025. 6. 10.", "😊", "☀️"),
            DiaryListEntity(2,"비 오는 날", "2025. 6. 8.", "😐", "🌧️"),
            DiaryListEntity(3, "행복한 순간", "2025. 6. 5.", "😄", "☀️"),
            DiaryListEntity(1,"즐거운 하루", "2025. 6. 10.", "😊", "☀️"),
            DiaryListEntity(2,"비 오는 날", "2025. 6. 8.", "😐", "🌧️"),
            DiaryListEntity(3, "행복한 순간", "2025. 6. 5.", "😄", "☀️"),
            DiaryListEntity(1,"즐거운 하루", "2025. 6. 10.", "😊", "☀️"),
            DiaryListEntity(2,"비 오는 날", "2025. 6. 8.", "😐", "🌧️"),
            DiaryListEntity(3, "행복한 순간", "2025. 6. 5.", "😄", "☀️"),
        )
        DiaryScreen(
            diaryItems = dummyList,
            onDiaryClick = TODO()
        )
    }
}