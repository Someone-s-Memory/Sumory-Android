package com.sumory.diary.view

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.sumory.design_system.icon.FilterIcon
import com.sumory.design_system.theme.SumoryTheme
import com.sumory.diary.view.component.DiaryItem
import com.sumory.model.entity.diary.DiaryListEntity
import com.sumory.ui.DevicePreviews

@Composable
fun DiaryScreen(
    modifier: Modifier = Modifier,
    diaryItems: List<DiaryListEntity>,
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
                    modifier = modifier
                        .fillMaxSize(),
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
fun DiaryScreenPreview(){

    val dummyList = listOf(
        DiaryListEntity(1,"즐거운 하루", "2025. 6. 10.", "😊", "☀️"),
        DiaryListEntity(2,"비 오는 날", "2025. 6. 8.", "😐", "🌧️"),
        DiaryListEntity(3, "행복한 순간", "2025. 6. 5.", "😄", "☀️")
    )

    DiaryScreen(
        diaryItems = dummyList,
        onDiaryClick = {}
    )
}

@DevicePreviews
@Composable
fun DiaryScreenPreviewEmpty() {
    DiaryScreen(
        diaryItems = emptyList(),
        onDiaryClick = {}
    )
}