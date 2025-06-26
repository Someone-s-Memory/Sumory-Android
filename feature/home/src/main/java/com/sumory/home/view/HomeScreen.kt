package com.sumory.home.view

import android.app.Activity
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.hilt.navigation.compose.hiltViewModel
import com.sumory.design_system.component.dialog.SumoryDialog
import com.sumory.design_system.theme.SumoryTheme
import com.sumory.home.viewmodel.HomeViewModel
import com.sumory.ui.DevicePreviews


@Composable
fun HomeScreenRoute(
    viewModel: HomeViewModel = hiltViewModel()
) {
    val petName by viewModel.petName.collectAsState()
    val affinity by viewModel.affinity.collectAsState()

    val context = LocalContext.current
    val activity = context as? Activity

    var showExitDialog by remember { mutableStateOf(false) }

    BackHandler {
        showExitDialog = true
    }

    if (showExitDialog) {
        SumoryDialog(
            title = "앱 종료",
            message = "정말 종료하시겠습니까?",
            onConfirm = {
                showExitDialog = false
                activity?.finish()
            },
            onDismiss = {
                showExitDialog = false
            }
        )
    }

    HomeScreen(
        petName = petName,
        affinity = affinity
    )
}

@Composable
fun HomeScreen(
    modifier: Modifier = Modifier,
    petName: String,
    affinity: Int,
){
    SumoryTheme { colors, typography ->
        Column(
            modifier.fillMaxSize()
                .background(color = colors.white),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = petName,
                style = typography.titleBold2,
                color = colors.black,
            )
            Row {
                Text(
                    text = "❤️ $affinity",
                    style = typography.captionBold1,
                    color = colors.black,
                )
            }
        }
    }
}

@DevicePreviews
@Composable
private fun HomeScreenPreview(){
    HomeScreen(
        petName = "나의 펫",
        affinity = 15
    )
}