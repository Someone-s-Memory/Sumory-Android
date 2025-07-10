package com.sumory.stat.view.component

import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.sumory.design_system.theme.SumoryTheme
import com.sumory.ui.DevicePreviews
import java.time.YearMonth

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MonthPickerBottomSheet(
    current: YearMonth?,
    diaryRange: ClosedRange<YearMonth>,
    onSelect: (YearMonth?) -> Unit,
    modifier: Modifier = Modifier
) {
    val sheetState = rememberModalBottomSheetState()
    var show by remember { mutableStateOf(false) }

    val months = remember(diaryRange) {
        val monthsList = mutableListOf<YearMonth>()
        var ym = diaryRange.endInclusive
        while (!ym.isBefore(diaryRange.start)) {
            monthsList.add(ym)
            ym = ym.minusMonths(1)
        }
        monthsList
    }

    SumoryTheme { colors, typography ->
        Column(modifier = modifier) {
            Text(
                text = current?.let { "${it.year}년 ${it.monthValue}월" } ?: "전체 기간",
                modifier = Modifier
                    .clickable(
                        indication = null,
                        interactionSource = remember { MutableInteractionSource() }
                    ) { show = true }
                    .padding(12.dp),
                color = colors.black,
                style = typography.bodyBold2
            )

            if (show) {
                ModalBottomSheet(
                    containerColor = colors.white,
                    onDismissRequest = { show = false },
                    sheetState = sheetState
                ) {
                    LazyColumn(modifier = Modifier.padding(16.dp)) {
                        item {
                            val isSelected = current == null
                            Text(
                                text = "전체 기간",
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .clickable {
                                        onSelect(null)
                                        show = false
                                    }
                                    .padding(12.dp),
                                color = if (isSelected) colors.darkPink else colors.black,
                                style = if (isSelected) typography.bodyBold2 else typography.bodyRegular2
                            )
                            Divider()
                        }
                        items(months) { ym ->
                            val isSelected = ym == current
                            Text(
                                text = "${ym.year}년 ${ym.monthValue}월",
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .clickable {
                                        onSelect(ym)
                                        show = false
                                    }
                                    .padding(12.dp),
                                color = if (isSelected) colors.darkPink else colors.black,
                                style = if (isSelected) typography.bodyBold2 else typography.bodyRegular2
                            )
                        }
                    }
                }
            }
        }
    }
}

@DevicePreviews
@Composable
fun MonthPickerBottomSheetPreview() {
    MonthPickerBottomSheet(
        current = YearMonth.of(2025, 7),
        diaryRange = YearMonth.of(2025, 6)..YearMonth.of(2025, 7),
        onSelect = {}
    )
}