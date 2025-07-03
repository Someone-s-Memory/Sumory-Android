package com.sumory.diary.view

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.sumory.design_system.icon.FilterIcon
import com.sumory.design_system.theme.SumoryTheme
import com.sumory.diary.view.component.DiaryItem
import com.sumory.diary.viewmodel.DiaryViewModel
import com.sumory.model.model.diary.AllDiaryResponseModel
import com.sumory.ui.DevicePreviews

@Composable
fun DiaryScreenRoute(
    viewModel: DiaryViewModel = hiltViewModel(),
    onDiaryClick: (Int) -> Unit
) {
    val diaryList by viewModel.diaryList.collectAsState()
    val isLoading by viewModel.isLoading.collectAsState()
    val errorMessage by viewModel.errorMessage.collectAsState()

    when {
        isLoading -> {
            Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                CircularProgressIndicator()
            }
        }

        errorMessage != null -> {
            DiaryScreen(
                diaryItems = emptyList(),
                onDiaryClick = onDiaryClick
            )
        }

        else -> {
            DiaryScreen(
                diaryItems = diaryList,
                onDiaryClick = onDiaryClick
            )
        }
    }
}

@Composable
fun DiaryScreen(
    modifier: Modifier = Modifier,
    diaryItems: List<AllDiaryResponseModel>,
    onDiaryClick: (Int) -> Unit
) {
    SumoryTheme { colors, typography ->
        Column(
            modifier
                .fillMaxSize()
                .background(colors.white)
                .padding(start = 16.dp, end = 16.dp, top = 16.dp),
        ) {
            Row(
                modifier = modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "모아보기",
                    style = typography.titleRegular3,
                    color = colors.black
                )
                Spacer(modifier = modifier.weight(1f))
                FilterIcon(
                    modifier = modifier.padding(start = 5.dp),
                    tint = colors.black
                )
            }

            Spacer(modifier = modifier.height(16.dp))

            if (diaryItems.isEmpty()) {
                Column(
                    modifier = modifier.fillMaxSize(),
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        text = "아직 작성된 일기가 없어요.\n캘린더에서 오늘의 감정을 기록해보세요 ☁️",
                        style = typography.bodyRegular2,
                        color = colors.gray700,
                        textAlign = TextAlign.Center,
                        modifier = Modifier.fillMaxWidth()
                    )
                }
            } else {
                LazyColumn(
                    modifier = modifier.fillMaxSize(),
                    verticalArrangement = Arrangement.spacedBy(12.dp),
                ) {
                    items(diaryItems) { item ->
                        DiaryItem(
                            item = item,
                            onClick = { onDiaryClick(item.id) }
                        )
                    }
                }
            }
        }
    }
}

@DevicePreviews
@Composable
private fun DiaryScreenPreview() {
    val dummyList = listOf(
        AllDiaryResponseModel(
            id = 1,
            title = "즐거운 하루",
            content = "오늘은 정말 행복했어요",
            feeling = "행복",
            weather = "맑음",
            date = "2025. 6. 10.",
            pictures = listOf("sample1"),
            userID = "user1"
        ),
        AllDiaryResponseModel(
            id = 2,
            title = "비 오는 날",
            content = "조금 우울했지만 괜찮았어요",
            feeling = "슬픔",
            weather = "비",
            date = "2025. 6. 8.",
            pictures = listOf("sample2"),
            userID = "user1"
        ),
        AllDiaryResponseModel(
            id = 3,
            title = "행복한 순간",
            content = "가족과 함께한 시간",
            feeling = "행복",
            weather = "맑음",
            date = "2025. 6. 5.",
            pictures = listOf("sample3"),
            userID = "user1"
        ),
    )

    DiaryScreen(
        diaryItems = dummyList,
        onDiaryClick = {}
    )
}

@DevicePreviews
@Composable
private fun DiaryScreenPreviewEmpty() {
    DiaryScreen(
        diaryItems = emptyList(),
        onDiaryClick = {}
    )
}
