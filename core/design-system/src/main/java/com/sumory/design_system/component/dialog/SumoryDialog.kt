package com.sumory.design_system.component.dialog

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.sumory.design_system.theme.SumoryTheme

@Composable
fun SumoryDialog(
    title: String,
    message: String,
    onConfirm: () -> Unit,
    onDismiss: () -> Unit,
    confirmText: String = "확인",
    dismissText: String = "취소"
) {
    SumoryTheme { colors, typography ->
        AlertDialog(
            onDismissRequest = onDismiss,
            title = {
                Text(
                    text = title,
                    style = typography.titleMedium1,
                    color = colors.black
                )
            },
            text = {
                Text(
                    text = message,
                    style = typography.bodyRegular2,
                    color = colors.gray700,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 8.dp)
                )
            },
            confirmButton = {
                TextButton(onClick = onConfirm) {
                    Text(
                        text = confirmText,
                        style = typography.bodyRegular2,
                        color = colors.darkPink
                    )
                }
            },
            dismissButton = {
                TextButton(onClick = onDismiss) {
                    Text(
                        text = dismissText,
                        style = typography.bodyRegular2,
                        color = colors.gray600
                    )
                }
            },
            shape = RoundedCornerShape(16.dp),
            containerColor = colors.white
        )
    }
}

@Preview
@Composable
fun SumoryDialogPreview() {
    SumoryDialog(
        title = "로그아웃",
        message = "정말 로그아웃 하시겠습니까?",
        onConfirm = {},
        onDismiss = {}
    )
}