package com.sumory.diary

import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.sumory.diary.view.DiaryDetailScreen
import com.sumory.diary.view.DiaryScreen
import com.sumory.diary.view.DiaryScreenRoute
import com.sumory.diary.view.DiaryWriteRoute
import com.sumory.diary.view.DiaryWriteScreen
import com.sumory.model.entity.diary.DiaryListEntity

const val diaryRoute = "diaryRoute"
const val diaryDetailRoute = "diaryDetailRoute"
const val diaryWriteRoute = "diaryWriteRoute"

fun NavGraphBuilder.diaryScreen(
    onDiaryClick: (Int) -> Unit
) {
    composable(diaryRoute) {
        DiaryScreenRoute(
            onDiaryClick = onDiaryClick
        )
    }
}

fun NavGraphBuilder.diaryDeatilScreen(
    onBackClick: () -> Unit
) {
    composable(diaryDetailRoute) {
        DiaryDetailScreen(
            date = "2025년 6월 10일 화요일",
            emotion = "😊",
            weather = "☀️",
            title = "즐거운 하루",
            content = "오늘은 정말 좋은 하루였다!",
            photoUrls = listOf(
                "https://cdn.discordapp.com/avatars/764002454022127616/87a1b4a7f7367319ea2772a6374c8862.webp?size=160",
                "https://picsum.photos/201/300",
                "https://picsum.photos/202/300"
            ),
            onEditClick = {},
            onBackClick = onBackClick
        )
    }
}

fun NavGraphBuilder.diaryWriteScreen(
    onBackClick: () -> Unit,
    onDiarySavedSuccess: () -> Unit
) {
    composable(diaryWriteRoute) {
        DiaryWriteRoute(
            onBackClick = onBackClick,
            onDiarySavedSuccess = onDiarySavedSuccess
        )
    }
}