package com.sumory.diary

import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.sumory.diary.view.DiaryDetailScreen
import com.sumory.diary.view.DiaryScreen
import com.sumory.model.entity.diary.DiaryListEntity

const val diaryRoute = "diaryRoute"
const val diaryDetailRoute = "diaryDetailRoute"

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
            onDiaryClick = {}
        )
    }
}

fun NavGraphBuilder.diaryDeatilScreen() {
    composable(diaryDetailRoute) {
        DiaryDetailScreen(
            date = "2025년 6월 10일 화요일",
            emotion = "😊",
            weather = "☀️",
            title = "즐거운 하루",
            content = "오늘은 정말 좋은 하루였다!",
            photoUrls = listOf(
                "https://picsum.photos/200/300",
                "https://picsum.photos/201/300",
                "https://picsum.photos/202/300"
            ),
            onEditClick = {},
            onBackClick = {}
        )
    }
}