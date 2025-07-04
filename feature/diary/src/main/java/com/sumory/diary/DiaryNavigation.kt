package com.sumory.diary

import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.sumory.diary.view.CalendarRoute
import com.sumory.diary.view.DiaryDetailRoute
import com.sumory.diary.view.DiaryScreenRoute
import com.sumory.diary.view.DiaryWriteRoute

const val diaryRoute = "diaryRoute"
const val diaryWriteRoute = "diaryWriteRoute"
const val calendarRoute = "calendarRoute"
const val diaryDetailRoute = "diaryDetailRoute/{diaryId}"

fun NavGraphBuilder.diaryScreen(
    onDiaryClick: (Int) -> Unit,
    onWriteClick: () -> Unit,
) {
    composable(diaryRoute) {
        DiaryScreenRoute(
            onDiaryClick = onDiaryClick,
            onWriteClick = onWriteClick
        )
    }
}

fun NavGraphBuilder.diaryDetailScreen(
    onBackClick: () -> Unit,
    onEditClick: (Int) -> Unit
) {
    composable(
        diaryDetailRoute,
        arguments = listOf(navArgument("diaryId") { type = NavType.IntType })
    ) { backStackEntry ->
        val diaryId = backStackEntry.arguments?.getInt("diaryId") ?: 0
        DiaryDetailRoute(
            diaryId = diaryId,
            onBackClick = onBackClick,
            onEditClick = { onEditClick(diaryId) }
        )
    }
}

fun NavGraphBuilder.diaryWriteScreen(
    onBackClick: () -> Unit,
    onDiarySavedSuccess: () -> Unit
) {
    composable(
        route = "$diaryWriteRoute?diaryId={diaryId}",
        arguments = listOf(navArgument("diaryId") {
            type = NavType.IntType
            defaultValue = -1
        })
    ) { backStackEntry ->
        val diaryId = backStackEntry.arguments?.getInt("diaryId")
        DiaryWriteRoute(
            diaryId = if (diaryId != -1) diaryId else null,
            onBackClick = onBackClick,
            onDiarySavedSuccess = onDiarySavedSuccess
        )
    }
}

fun NavGraphBuilder.calendarScreen(
    onWriteClick: () -> Unit,
    onDiaryClick: (Int) -> Unit
) {
    composable(calendarRoute) {
        CalendarRoute (
            onDiaryClick = onDiaryClick,
            onWriteClick = onWriteClick
        )
    }
}