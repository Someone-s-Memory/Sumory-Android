package com.sumory.diary.view

import android.Manifest
import android.app.Activity
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Build
import android.util.Log
import androidx.activity.compose.BackHandler
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsPressedAsState
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
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.platform.LocalSavedStateRegistryOwner
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.viewmodel.compose.LocalViewModelStoreOwner
import com.sumory.design_system.component.dialog.SumoryDialog
import com.sumory.design_system.component.textfield.SumoryTextField
import com.sumory.design_system.component.toast.SumoryToast
import com.sumory.design_system.icon.LeftArrowIcon
import com.sumory.design_system.icon.SaveIcon
import com.sumory.design_system.theme.SumoryTheme
import com.sumory.diary.view.component.DiaryImagePickerSection
import com.sumory.diary.viewmodel.DiaryWriteViewModel
import com.sumory.diary.viewmodel.uistate.DiaryWriteUiState
import com.sumory.model.type.DiaryFeeling
import com.sumory.model.type.DiaryWeather
import com.sumory.ui.DevicePreviews
import com.sumory.ui.mapper.iconRes
import java.time.format.DateTimeFormatter
import java.util.Locale

@Composable
fun DiaryWriteRoute(
    diaryId: Int?,
    onBackClick: () -> Unit = {},
    onDiarySavedSuccess: () -> Unit = {},
    viewModel: DiaryWriteViewModel = hiltViewModel()
) {
    val context = LocalContext.current
    val activity = context as Activity

    val lifecycleOwner = LocalLifecycleOwner.current
    val viewModelStoreOwner = LocalViewModelStoreOwner.current
    val savedStateRegistryOwner = LocalSavedStateRegistryOwner.current

    val diaryDate by viewModel.diaryDate.collectAsState()
    val displayDate = diaryDate.format(
        DateTimeFormatter.ofPattern("yyyy년 M월 d일 E요일", Locale.KOREAN)
    )

    val title by viewModel.title.collectAsState()
    val content by viewModel.content.collectAsState()
    //val contentLength by viewModel.contentLength.collectAsState()
    val selectedFeeling by viewModel.selectedFeeling.collectAsState()
    val selectedWeather by viewModel.selectedWeather.collectAsState()
    val writeState by viewModel.diaryWriteState.collectAsState()
    val imageUris by viewModel.imageUris.collectAsState()

    var showSaveDialog by remember { mutableStateOf(false) }
    var showExitDialog by remember { mutableStateOf(false) }

    if (showSaveDialog) {
        SumoryDialog(
            title = "일기 작성 완료",
            message = "일기를 저장하시겠습니까?",
            onConfirm = {
                showSaveDialog = false
                viewModel.postDiary(context = context)
            },
            onDismiss = {
                showSaveDialog = false
            }
        )
    }

    BackHandler {
        showExitDialog = true
    }

    if (showExitDialog) {
        SumoryDialog(
            title = "작성 종료",
            message = "작성을 종료하시겠습니까?\n작성한 내용은 저장되지 않습니다.",
            onConfirm = {
                showExitDialog = false
                onBackClick()
            },
            onDismiss = {
                showExitDialog = false
            }
        )
    }

    // 권한 요청 대상
    val permissionToRequest = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
        Manifest.permission.READ_MEDIA_IMAGES
    } else {
        Manifest.permission.READ_EXTERNAL_STORAGE
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

    // 갤러리 오픈 성공/실패 처리
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

    LaunchedEffect(selectedFeeling) {
        Log.d("DiaryWrite", "선택된 감정: $selectedFeeling")
    }

    LaunchedEffect(selectedWeather) {
        Log.d("DiaryWrite", "선택된 날씨: $selectedWeather")
    }

    DiaryWriteScreen(
        date = displayDate,
        title = title,
        onTitleChange = viewModel::updateTitle,
        content = content,
        onContentChange = viewModel::updateContent,
        onBackClick = { showExitDialog = true },
        selectedFeeling = selectedFeeling,
        onFeelingSelected = viewModel::selectFeeling,
        selectedWeather = selectedWeather,
        onWeatherSelected = viewModel::selectWeather,
        onSaveClick = { showSaveDialog = true },
        imageUris = imageUris,
        onAddImageClick = viewModel::onImageAddClick,
        onRemoveImageClick = viewModel::removeImage,
        //contentLength = contentLength
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
    selectedFeeling: DiaryFeeling?,
    onFeelingSelected: (DiaryFeeling) -> Unit,
    selectedWeather: DiaryWeather?,
    onWeatherSelected: (DiaryWeather) -> Unit,
    onSaveClick: () -> Unit,
    imageUris: List<Uri>,
    onAddImageClick: () -> Unit,
    onRemoveImageClick: (Uri) -> Unit,
    //contentLength: Int
) {
    SumoryTheme { colors, typography ->
        Column(
            modifier = modifier
                .fillMaxSize()
                .background(colors.gray50)
                .padding(16.dp)
                .padding(WindowInsets.systemBars.asPaddingValues())
        ) {
            Row(
                modifier = modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                val leftArrowInteractionSource = remember { MutableInteractionSource() }
                val leftArrowPressed by leftArrowInteractionSource.collectIsPressedAsState()
                LeftArrowIcon(
                    modifier = modifier
                        .clickable(
                            indication = null,
                            interactionSource = leftArrowInteractionSource
                    ) { onBackClick() }
                    .alpha(if (leftArrowPressed) 0.6f else 1f),
                    tint = colors.black
                )
                Spacer(modifier = modifier.weight(1f))
                Text(
                    text = date,
                    style = typography.titleBold3,
                    color = colors.black
                )
                Spacer(modifier = modifier.weight(1f))

                val saveInteractionSource = remember { MutableInteractionSource() }
                val savePressed by saveInteractionSource.collectIsPressedAsState()
                Box(
                    modifier = modifier
                        .size(24.dp)
                        .clip(RoundedCornerShape(5.dp))
                        .background(colors.darkPink)
                        .clickable(
                            indication = null,
                            interactionSource = saveInteractionSource
                        ) { onSaveClick() }
                        .alpha(if (savePressed) 0.6f else 1f),
                    contentAlignment = Alignment.Center
                ) {
                    SaveIcon(
                        modifier = modifier.size(18.dp),
                        tint = colors.white
                    )
                }
            }

            Box(modifier = modifier.padding(top = 30.dp)) {
                SumoryTextField(
                    textState = title,
                    placeHolder = "제목을 입력하세요",
                    focusText = "제목을 입력하세요",
                    onTextChange = onTitleChange,
                    icon = {}
                )
            }

            Spacer(modifier = modifier.height(16.dp))

            Text(text = "감정", style = typography.bodyBold1, color = colors.black)
            Spacer(modifier = modifier.height(8.dp))
            Row(horizontalArrangement = Arrangement.spacedBy(12.dp)) {
                DiaryFeeling.values().forEach { feeling ->
                    val isSelected = selectedFeeling == feeling
                    val feelingInteractionSource = remember { MutableInteractionSource() }
                    val feelingPressed by feelingInteractionSource.collectIsPressedAsState()
                    Box(
                        modifier = modifier
                            .size(48.dp)
                            .clip(RoundedCornerShape(12.dp))
                            .background(
                                if (isSelected) {
                                    if (feelingPressed) colors.pinkSoftBackground.copy(alpha = 0.6f)
                                    else colors.pinkSoftBackground
                                } else {
                                    if (feelingPressed) colors.gray50.copy(alpha = 0.6f)
                                    else colors.gray50
                                }
                            )
                            .border(
                                1.dp,
                                if (isSelected) {
                                    if (feelingPressed) colors.main.copy(alpha = 0.6f)
                                    else colors.main
                                } else {
                                    if (feelingPressed) colors.gray500.copy(alpha = 0.6f)
                                    else colors.gray500
                                },
                                RoundedCornerShape(12.dp)
                            )
                            .clickable(
                                indication = null,
                                interactionSource = feelingInteractionSource
                            ) { onFeelingSelected(feeling) },
                            //.alpha(if (feelingPressed) 0.6f else 1.0f),
                        contentAlignment = Alignment.Center
                    ) {
                        Image(
                            painter = painterResource(id = feeling.iconRes()),
                            contentDescription = feeling.value,
                            modifier = modifier.size(24.dp),
                        )
                    }
                }
            }

            Spacer(modifier = modifier.height(12.dp))

            Text(text = "날씨", style = typography.bodyBold1, color = colors.black)
            Spacer(modifier = modifier.height(8.dp))
            Row(horizontalArrangement = Arrangement.spacedBy(12.dp)) {
                DiaryWeather.values().forEach { weather ->
                    val isSelected = selectedWeather == weather
                    val weatherInteractionSource = remember { MutableInteractionSource() }
                    val weatherPressed by weatherInteractionSource.collectIsPressedAsState()
                    Box(
                        modifier = modifier
                            .size(48.dp)
                            .clip(RoundedCornerShape(12.dp))
                            .background(
                                if (isSelected) colors.pinkSoftBackground else colors.gray50
                            )
                            .border(
                                1.dp,
                                if (isSelected) colors.main else colors.gray500,
                                RoundedCornerShape(12.dp)
                            )
                            .clickable(
                                indication = null,
                                interactionSource = weatherInteractionSource
                            ) { onWeatherSelected(weather) },
                            //.alpha(if (weatherPressed) 0.6f else 1.0f),
                        contentAlignment = Alignment.Center
                    ) {
                        Image(
                            painter = painterResource(id = weather.iconRes()),
                            contentDescription = weather.value,
                            modifier = modifier.size(24.dp)
                        )
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

//            Text(
//                text = "$contentLength/1000",
//                style = typography.bodyRegular2,
//                color = colors.gray700,
//                modifier = Modifier
//                    .fillMaxWidth(),
//                textAlign = TextAlign.End
//            )
//
//            Spacer(modifier = modifier.height(5.dp))


            SumoryTextField(
                modifier = modifier.fillMaxHeight(),
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
        selectedFeeling = null,
        onFeelingSelected = {},
        selectedWeather = null,
        onWeatherSelected = {},
        onSaveClick = {},
        imageUris = emptyList(),
        onAddImageClick = {},
        onRemoveImageClick = {},
        //contentLength = 720
    )
}