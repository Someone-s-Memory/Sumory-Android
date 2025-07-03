package com.sumory.diary.view

import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.asPaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.systemBars
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.Surface
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
import androidx.compose.ui.draw.clip
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.platform.LocalSavedStateRegistryOwner
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.viewmodel.compose.LocalViewModelStoreOwner
import coil.compose.rememberAsyncImagePainter
import com.sumory.design_system.component.dialog.SumoryDialog
import com.sumory.design_system.component.toast.SumoryToast
import com.sumory.design_system.icon.DeleteIcon
import com.sumory.design_system.icon.EditIcon
import com.sumory.design_system.icon.LeftArrowIcon
import com.sumory.design_system.theme.SumoryTheme
import com.sumory.diary.viewmodel.DiaryDetailViewModel
import com.sumory.diary.viewmodel.mapper.iconRes
import com.sumory.diary.viewmodel.uistate.DiaryDetailUiState
import com.sumory.model.mapper.diary.toDiaryFeeling
import com.sumory.ui.DevicePreviews

@Composable
fun DiaryDetailRoute(
    diaryId: Int,
    viewModel: DiaryDetailViewModel = hiltViewModel(),
    onBackClick: () -> Unit,
    onEditClick: () -> Unit = {}
) {
    val context = LocalContext.current
    val lifecycleOwner = LocalLifecycleOwner.current
    val viewModelStoreOwner = LocalViewModelStoreOwner.current
    val savedStateRegistryOwner = LocalSavedStateRegistryOwner.current

    val diaryDetail by viewModel.diaryDetail.collectAsState()
    val isLoading by viewModel.isLoading.collectAsState()
    val errorMessage by viewModel.errorMessage.collectAsState()
    val deleteState by viewModel.diaryDetailState.collectAsState()
    var showDialog by remember { mutableStateOf(false) }

    LaunchedEffect(diaryId) {
        viewModel.loadDiaryDetail(diaryId)
    }

    LaunchedEffect(deleteState) {
        when (deleteState) {
            is DiaryDetailUiState.Success -> {
                SumoryToast(context).showToast(
                    message = "일기가 삭제되었습니다.",
                    duration = 1000,
                    icon = null,
                    lifecycleOwner = lifecycleOwner,
                    viewModelStoreOwner = viewModelStoreOwner!!,
                    savedStateRegistryOwner = savedStateRegistryOwner
                )
                onBackClick()
                viewModel.resetState()
            }

            is DiaryDetailUiState.Error -> {
                SumoryToast(context).showToast(
                    message = (deleteState as DiaryDetailUiState.Error).message,
                    duration = 1000,
                    icon = null,
                    lifecycleOwner = lifecycleOwner,
                    viewModelStoreOwner = viewModelStoreOwner!!,
                    savedStateRegistryOwner = savedStateRegistryOwner
                )
                viewModel.resetState()
            }

            else -> {}
        }
    }

    if (showDialog) {
        SumoryDialog(
            title = "일기 삭제",
            message = "정말로 이 일기를 삭제하시겠어요?",
            onConfirm = {
                showDialog = false
                viewModel.deleteDiary()
            },
            onDismiss = { showDialog = false }
        )
    }

    when {
        isLoading -> Box(
            Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            CircularProgressIndicator()
        }

        errorMessage != null -> Box(
            Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            Text(text = errorMessage ?: "알 수 없는 오류")
        }

        diaryDetail != null -> {
            DiaryDetailScreen(
                date = viewModel.formatDateWithDayOfWeek(diaryDetail!!.date),
                emotion = diaryDetail!!.feeling,
                weather = diaryDetail!!.weather,
                title = diaryDetail!!.title,
                content = diaryDetail!!.content,
                photoUrls = diaryDetail!!.pictures,
                onBackClick = onBackClick,
                onEditClick = onEditClick,
                onDeleteClick = { showDialog = true } // 여기서 다이얼로그 열기
            )
        }

        else -> Box(
            Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            Text(text = "일기 상세 정보가 없습니다.")
        }
    }
}


@Composable
fun DiaryDetailScreen(
    modifier: Modifier = Modifier,
    date: String,
    emotion: String,
    weather: String,
    title: String,
    content: String,
    photoUrls: List<String> = emptyList(),
    onEditClick: () -> Unit,
    onBackClick: () -> Unit,
    onDeleteClick: () -> Unit
) {
    var zoomedImageUrl by remember { mutableStateOf<String?>(null) }

    SumoryTheme { colors, typography ->
        Column(
            modifier = modifier
                .fillMaxSize()
                .background(colors.white)
                .padding(16.dp)
                .padding(WindowInsets.systemBars.asPaddingValues())
        ) {
            Row(
                modifier = modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                LeftArrowIcon(
                    modifier = modifier
                        .clickable { onBackClick() },
                    tint = colors.black
                )
                Spacer(modifier.width(29.dp))
                Spacer(modifier.weight(1f))
                Text(
                    text = date,
                    style = typography.titleBold3,
                    color = colors.black
                )
                Spacer(modifier.weight(1f))
                EditIcon(
                    modifier = modifier
                        .clickable { onEditClick() },
                    tint = colors.black
                )
                Spacer(modifier.width(5.dp))
                DeleteIcon(
                    modifier = modifier
                        .clickable { onDeleteClick() },
                    tint = colors.error
                )
            }

            Spacer(modifier.height(16.dp))

            Row(
                modifier = modifier
                    .fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center
            ) {
                Surface(
                    shape = RoundedCornerShape(20.dp),
                    color = colors.gray50,
                    modifier = Modifier.padding(vertical = 4.dp)
                ) {
                    val feelingIcon = emotion.toDiaryFeeling()
                    Icon(
                        painter = painterResource(feelingIcon.iconRes()),
                        contentDescription = "기분 아이콘",
                        modifier = Modifier.size(24.dp),
                    )
                }
                Spacer(modifier.width(15.dp))
                Surface(
                    shape = RoundedCornerShape(20.dp),
                    color = colors.gray50,
                    modifier = Modifier.padding(vertical = 4.dp)
                ) {
                    val weatherIcon = weather.toDiaryFeeling()
                    Icon(
                        painter = painterResource(weatherIcon.iconRes()),
                        contentDescription = "기분 아이콘",
                        modifier = Modifier.size(24.dp),
                    )
                }
            }

            Spacer(modifier.height(24.dp))

            Text(text = title, style = typography.titleBold2, color = colors.black)
            Divider(modifier = modifier.padding(vertical = 12.dp), thickness = 1.dp, color = colors.gray200)

            if (photoUrls.isNotEmpty()) {
                LazyRow(horizontalArrangement = Arrangement.spacedBy(12.dp)) {
                    items(photoUrls) { url ->
                        Image(
                            painter = rememberAsyncImagePainter(url),
                            contentDescription = "Diary photo",
                            modifier = modifier
                                .size(120.dp)
                                .clip(RoundedCornerShape(8.dp))
                                .clickable { zoomedImageUrl = url },
                            contentScale = ContentScale.Crop,
                        )
                    }
                }
            }

            zoomedImageUrl?.let { url ->
                Dialog(onDismissRequest = { zoomedImageUrl = null }) {
                    Box(
                        modifier = modifier
                            .fillMaxSize()
                            .background(colors.black.copy(alpha = 0.8f))
                            .pointerInput(Unit) {
                                detectTapGestures(onTap = { zoomedImageUrl = null })
                            },
                        contentAlignment = Alignment.Center
                    ) {
                        Image(
                            painter = rememberAsyncImagePainter(url),
                            contentDescription = "Zoomed Image",
                            modifier = modifier
                                .fillMaxSize()
                                .clip(RoundedCornerShape(12.dp)),
                            contentScale = ContentScale.Fit
                        )
                    }
                }
            }

            Spacer(modifier.height(20.dp))

            Text(text = content, style = typography.bodyRegular2, color = colors.black)
        }
    }
}

@DevicePreviews
@Composable
private fun DiaryDetailScreenPreview() {
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
        onBackClick = {},
        onDeleteClick = {}
    )
}
