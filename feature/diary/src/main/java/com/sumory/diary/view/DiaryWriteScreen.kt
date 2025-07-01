package com.sumory.diary.view

import android.app.Activity
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Build
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.asPaddingValues
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.systemBars
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.platform.LocalSavedStateRegistryOwner
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.viewmodel.compose.LocalViewModelStoreOwner
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.isGranted
import com.google.accompanist.permissions.rememberPermissionState
import com.sumory.design_system.component.textfield.SumoryTextField
import com.sumory.design_system.component.toast.SumoryToast
import com.sumory.design_system.icon.LeftArrowIcon
import com.sumory.design_system.icon.SaveIcon
import com.sumory.design_system.theme.SumoryTheme
import com.sumory.diary.view.component.DiaryImagePickerSection
import com.sumory.diary.viewmodel.DiaryWriteViewModel
import com.sumory.diary.viewmodel.uistate.DiaryWriteUiState
import com.sumory.ui.DevicePreviews
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.util.Locale

@Composable
fun DiaryWriteRoute(
    onBackClick: () -> Unit = {},
    onDiarySavedSuccess: () -> Unit = {},
    viewModel: DiaryWriteViewModel = hiltViewModel()
) {
    val context = LocalContext.current
    val activity = context as Activity

    val lifecycleOwner = LocalLifecycleOwner.current
    val viewModelStoreOwner = LocalViewModelStoreOwner.current
    val savedStateRegistryOwner = LocalSavedStateRegistryOwner.current

    val today = LocalDate.now()
    val apiDate = today.format(DateTimeFormatter.ISO_DATE) // "yyyy-MM-dd"
    val displayDate = today.format(
        DateTimeFormatter.ofPattern("yyyy년 M월 d일 E요일", Locale.KOREAN)
    )

    val title by viewModel.title.collectAsState()
    val content by viewModel.content.collectAsState()
    val selectedEmotion by viewModel.selectedEmotion.collectAsState()
    val selectedWeather by viewModel.selectedWeather.collectAsState()
    val writeState by viewModel.diaryWriteState.collectAsState()
    val imageUris by viewModel.imageUris.collectAsState()

    // 권한 요청 대상
    val permissionToRequest = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
        android.Manifest.permission.READ_MEDIA_IMAGES
    } else {
        android.Manifest.permission.READ_EXTERNAL_STORAGE
    }

    // 권한 요청 결과 처리
    val permissionLauncher = rememberLauncherForActivityResult(
        ActivityResultContracts.RequestPermission()
    ) { granted ->
        if (granted) {
            viewModel.triggerGalleryLaunch()
        } else {
            SumoryToast(context = context).showToast(
                message = "사진 접근 권한이 필요합니다.",
                duration = 1000,
                lifecycleOwner = lifecycleOwner,
                viewModelStoreOwner = viewModelStoreOwner!!,
                savedStateRegistryOwner = savedStateRegistryOwner
            )
        }
    }

    // 갤러리 런처
    val galleryLauncher = rememberLauncherForActivityResult(
        ActivityResultContracts.GetContent()
    ) { uri: Uri? ->
        uri?.let { viewModel.addImage(it) }
    }

    LaunchedEffect(Unit) {
        viewModel.openGalleryEvent.collect {
            when {
                ContextCompat.checkSelfPermission(context, permissionToRequest) == PackageManager.PERMISSION_GRANTED -> {
                    galleryLauncher.launch("image/*")
                }

                !ActivityCompat.shouldShowRequestPermissionRationale(activity, permissionToRequest) -> {
                    permissionLauncher.launch(permissionToRequest)
                }

                else -> {
                    SumoryToast(context = context).showToast(
                        message = "사진 접근 권한이 거부되었습니다.\n설정에서 수동으로 허용해주세요.",
                        duration = 2000,
                        lifecycleOwner = lifecycleOwner,
                        viewModelStoreOwner = viewModelStoreOwner!!,
                        savedStateRegistryOwner = savedStateRegistryOwner
                    )
                }
            }
        }
    }


    // 일기 작성 성공/실패 처리
    LaunchedEffect(writeState) {
        when (writeState) {
            is DiaryWriteUiState.Success -> {
                onDiarySavedSuccess()
                viewModel.resetWriteState()
            }

            is DiaryWriteUiState.Error -> {
                SumoryToast(context = context).showToast(
                    message = (writeState as DiaryWriteUiState.Error).message,
                    duration = 1000,
                    lifecycleOwner = lifecycleOwner,
                    viewModelStoreOwner = viewModelStoreOwner!!,
                    savedStateRegistryOwner = savedStateRegistryOwner
                )
                viewModel.resetWriteState()
            }

            else -> Unit
        }
    }

    // 실제 UI
    DiaryWriteScreen(
        date = displayDate,
        title = title,
        onTitleChange = viewModel::updateTitle,
        content = content,
        onContentChange = viewModel::updateContent,
        onBackClick = onBackClick,
        selectedEmotion = selectedEmotion,
        onEmotionSelected = viewModel::selectEmotion,
        selectedWeather = selectedWeather,
        onWeatherSelected = viewModel::selectWeather,
        onSaveClick = {
            viewModel.postDiary(date = apiDate)
        },
        imageUris = imageUris,
        onAddImageClick = viewModel::onImageAddClick,
        onRemoveImageClick = viewModel::removeImage
    )
}

@Composable
fun DiaryWriteScreen(
    modifier: Modifier = Modifier,
    date: String,
    title: String,
    onTitleChange: (String) -> Unit,
    content: String,
    onContentChange: (String) -> Unit,
    onBackClick: () -> Unit,
    selectedEmotion: String?,
    onEmotionSelected: (String) -> Unit,
    selectedWeather: String?,
    onWeatherSelected: (String) -> Unit,
    onSaveClick: () -> Unit,
    imageUris: List<Uri>,
    onAddImageClick: () -> Unit,
    onRemoveImageClick: (Uri) -> Unit,
) {
    val emotionList = listOf("😊", "😢", "😳", "😠", "😆", "🤔")
    val weatherList = listOf("🌞", "☁️", "🌧️", "❄️", "🌩️", "🌈")

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
                    modifier = modifier.clickable { onBackClick() },
                    tint = colors.black
                )
                Spacer(modifier = modifier.weight(1f))
                Text(
                    text = date,
                    style = typography.titleBold3,
                    color = colors.black
                )
                Spacer(modifier = modifier.weight(1f))
                Box(
                    modifier = modifier
                        .size(24.dp)
                        .clip(RoundedCornerShape(5.dp))
                        .background(colors.darkPink)
                        .clickable { onSaveClick() },
                    contentAlignment = Alignment.Center
                ) {
                    SaveIcon(
                        modifier = modifier
                            .size(18.dp),
                        tint = colors.white
                    )
                }
            }

            Box(
                modifier = modifier
                    .padding(top = 30.dp)
            ) {
                SumoryTextField(
                    textState = title,
                    placeHolder = "제목을 입력하세요",
                    focusText = "제목을 입력하세요",
                    onTextChange = onTitleChange,
                    icon = {}
                )
            }

            Text(
                text = "감정",
                style = typography.bodyBold1,
                color = colors.black
            )
            Spacer(modifier = modifier.height(8.dp))
            Row(horizontalArrangement = Arrangement.spacedBy(12.dp)) {
                emotionList.forEach { emoji ->
                    Box(
                        modifier = modifier
                            .size(48.dp)
                            .clip(RoundedCornerShape(12.dp))
                            .background(
                                if (selectedEmotion == emoji) colors.pinkSoftBackground else colors.white
                            )
                            .border(
                                1.dp,
                                if (selectedEmotion == emoji) colors.main else colors.white,
                                RoundedCornerShape(12.dp)
                            )
                            .clickable { onEmotionSelected(emoji) },
                        contentAlignment = Alignment.Center
                    ) {
                        Text(text = emoji, fontSize = 24.sp)
                    }
                }
            }

            Spacer(modifier = modifier.height(12.dp))

            Text(
                text = "날씨",
                style = typography.bodyBold1,
                color = colors.black
            )
            Spacer(modifier = modifier.height(8.dp))
            Row(horizontalArrangement = Arrangement.spacedBy(12.dp)) {
                weatherList.forEach { emoji ->
                    Box(
                        modifier = modifier
                            .size(48.dp)
                            .clip(RoundedCornerShape(12.dp))
                            .background(
                                if (selectedWeather == emoji) colors.pinkSoftBackground else colors.white
                            )
                            .border(
                                1.dp,
                                if (selectedWeather == emoji) colors.main else colors.white,
                                RoundedCornerShape(12.dp)
                            )
                            .clickable { onWeatherSelected(emoji) },
                        contentAlignment = Alignment.Center
                    ) {
                        Text(text = emoji, fontSize = 24.sp)
                    }
                }
            }

            Spacer(modifier = modifier.height(12.dp))

            DiaryImagePickerSection(
                imageUris = imageUris,
                onAddImageClick = onAddImageClick,
                onRemoveImageClick = onRemoveImageClick
            )

            Spacer(modifier = modifier.height(12.dp))

            SumoryTextField(
                modifier = modifier
                    .fillMaxHeight(),
                textState = content,
                placeHolder = "오늘 하루는 어땠나요?",
                focusText = "오늘 하루는 어땠나요?",
                singleLine = false,
                onTextChange = onContentChange,
                icon = {}
            )

            Spacer(modifier = modifier.height(32.dp))
        }
    }
}

@DevicePreviews
@Composable
private fun DiaryWriteScreenPreview() {
    DiaryWriteScreen(
        date = "2025년 7월 10일 수요일",
        title = "",
        content = "",
        onTitleChange = {},
        onContentChange = {},
        onBackClick = {},
        selectedEmotion = null,
        onEmotionSelected = {},
        selectedWeather = null,
        onWeatherSelected = {},
        onSaveClick = {},
        imageUris = emptyList(),
        onAddImageClick = {},
        onRemoveImageClick = {}
    )
}
