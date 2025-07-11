package com.sumory.design_system.component.dialog

import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsPressedAsState
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
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
                val confirmInteraction = remember { MutableInteractionSource() }
                val confirmPressed by confirmInteraction.collectIsPressedAsState()
                Text(
                    text = confirmText,
                    style = typography.bodyRegular2,
                    color = colors.darkPink,
                    modifier = Modifier
                        .clickable(
                            indication = null,
                            interactionSource = confirmInteraction
                        ) { onConfirm() }
                        .padding(8.dp)
                        .alpha(if (confirmPressed) 0.6f else 1f)
                )
            },
            dismissButton = {
                val dismissInteraction = remember { MutableInteractionSource() }
                val dismissPressed by dismissInteraction.collectIsPressedAsState()
                Text(
                    text = dismissText,
                    style = typography.bodyRegular2,
                    color = colors.gray600,
                    modifier = Modifier
                        .clickable(
                            indication = null,
                            interactionSource = dismissInteraction
                        ) { onDismiss() }
                        .padding(8.dp)
                        .alpha(if (dismissPressed) 0.6f else 1f)
                )
            },
            shape = RoundedCornerShape(16.dp),
            containerColor = colors.white
        )
    }
}

@Preview
@Composable
private fun SumoryDialogPreview() {
    SumoryDialog(
        title = "로그아웃",
        message = "정말 로그아웃 하시겠습니까?",
        onConfirm = {},
        onDismiss = {}
    )
}