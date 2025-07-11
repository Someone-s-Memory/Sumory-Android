package com.sumory.diary.view

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsPressedAsState
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.sumory.design_system.component.dropdownmenu.SumoryDropdownMenu
import com.sumory.design_system.icon.DropdownIcon
import com.sumory.design_system.icon.EditIcon
import com.sumory.design_system.theme.SumoryTheme
import com.sumory.diary.view.component.DiaryItem
import com.sumory.diary.viewmodel.DiarySortType
import com.sumory.diary.viewmodel.DiaryViewModel
import com.sumory.model.model.diary.AllDiaryResponseModel
import com.sumory.ui.DevicePreviews

@Composable
fun DiaryScreenRoute(
    viewModel: DiaryViewModel = hiltViewModel(),
    onDiaryClick: (Int) -> Unit,
    onWriteClick: () -> Unit
) {
    val diaryList by viewModel.diaryList.collectAsState()
    val isLoading by viewModel.isLoading.collectAsState()
    val errorMessage by viewModel.errorMessage.collectAsState()
    val sortType by viewModel.sortType.collectAsState()

    LaunchedEffect(Unit) {
        viewModel.fetchDiaries()
    }

    when {
        isLoading -> {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                CircularProgressIndicator()
            }
        }
        errorMessage != null -> {
            DiaryScreen(
                diaryItems = emptyList(),
                onDiaryClick = onDiaryClick,
                onWriteClick = onWriteClick,
                sortType = sortType,
                onChangeSortType = viewModel::changeSortType
            )
        }
        else -> {
            DiaryScreen(
                diaryItems = diaryList,
                onDiaryClick = onDiaryClick,
                onWriteClick = onWriteClick,
                sortType = sortType,
                onChangeSortType = viewModel::changeSortType
            )
        }
    }
}

@Composable
fun DiaryScreen(
    modifier: Modifier = Modifier,
    diaryItems: List<AllDiaryResponseModel>,
    onDiaryClick: (Int) -> Unit,
    onWriteClick: () -> Unit,
    sortType: DiarySortType,
    onChangeSortType: (DiarySortType) -> Unit
) {
    var expanded by remember { mutableStateOf(false) }
    val sortOptions = listOf("최신순", "오래된순")
    val selectedIndex = if (sortType == DiarySortType.LATEST) 0 else 1

    SumoryTheme { colors, typography ->
        Column(
            modifier = modifier
                .fillMaxSize()
                .background(colors.gray50)
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

                val sortInteractionSource = remember { MutableInteractionSource() }
                val sortPressed by sortInteractionSource.collectIsPressedAsState()
                Box(
                    modifier = Modifier
                        .clickable(
                            indication = null,
                            interactionSource = sortInteractionSource
                        ) { expanded = !expanded }
                        .padding(horizontal = 8.dp)
                        .alpha(if (sortPressed) 0.6f else 1.0f)
                ) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Text(
                            text = sortOptions[selectedIndex],
                            color = colors.black,
                            style = typography.bodyRegular2
                        )
                        DropdownIcon(
                            modifier
                                .size(20.dp),
                            tint = colors.black
                        )
                    }

                    SumoryDropdownMenu(
                        expanded = expanded,
                        onDismissRequest = { expanded = false },
                        items = sortOptions,
                        selectedIndex = selectedIndex,
                        onItemSelected = { index ->
                            expanded = false
                            onChangeSortType(
                                if (index == 0) DiarySortType.LATEST else DiarySortType.OLDEST
                            )
                        },
                        modifier.padding(end = 15.dp)
                    )
                }

                val editInteractionSource = remember { MutableInteractionSource() }
                val editPressed by editInteractionSource.collectIsPressedAsState()
                EditIcon(
                    modifier = modifier
                        .padding(start = 5.dp)
                        .clickable(
                            indication = null,
                            interactionSource = editInteractionSource
                        ) { onWriteClick() }
                        .alpha(if (editPressed) 0.6f else 1.0f),
                    tint = colors.black
                )
            }

            Spacer(modifier = modifier.height(12.dp))

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
                        modifier = modifier.fillMaxWidth()
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
private fun DiaryScreenPreviewEmpty() {
    DiaryScreen(
        diaryItems = emptyList(),
        onDiaryClick = {},
        onWriteClick = {},
        sortType = DiarySortType.LATEST,
        onChangeSortType = {}
    )
}
