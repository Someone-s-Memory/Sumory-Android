package com.sumory.design_system.component.dropdownmenu

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsPressedAsState
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Popup
import androidx.compose.ui.window.PopupProperties
import com.sumory.design_system.theme.SumoryTheme

@Composable
fun SumoryDropdownMenu(
    expanded: Boolean,
    onDismissRequest: () -> Unit,
    items: List<String>,
    selectedIndex: Int,
    onItemSelected: (Int) -> Unit,
    modifier: Modifier = Modifier
) {
    if (!expanded) return

    SumoryTheme { colors, typography ->
        Popup(
            onDismissRequest = onDismissRequest,
            properties = PopupProperties(focusable = true)
        ) {
            Box(
                modifier = modifier
                    .background(Color.White, RoundedCornerShape(8.dp))
                    .border(1.dp, Color.Gray, RoundedCornerShape(8.dp))
                    .padding(vertical = 4.dp, horizontal = 5.dp)
                    .width(125.dp)
            ) {
                LazyColumn(
                    modifier = Modifier.heightIn(max = 300.dp)
                ) {
                    itemsIndexed(items) { index, item ->
                        val dropdownInteractionSource = remember { MutableInteractionSource() }
                        val dropdownPressed by dropdownInteractionSource.collectIsPressedAsState()
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .clickable(
                                    indication = null,
                                    interactionSource = dropdownInteractionSource
                                ) {
                                    onItemSelected(index)
                                    onDismissRequest()
                                }
                                .padding(horizontal = 12.dp, vertical = 8.dp)
                                .alpha(if (dropdownPressed) 0.6f else 1.0f),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text(
                                text = item,
                                color = if (index == selectedIndex) colors.main else colors.black,
                                style = typography.bodyRegular2
                            )
                        }
                    }
                }
            }
        }
    }
}

@Preview
@Composable
fun SumoryDropdownMenuSample() {
    var expanded by remember { mutableStateOf(false) }
    var selectedIndex by remember { mutableStateOf(0) }

    Box(modifier = Modifier.padding(40.dp)) {
        SumoryDropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = !expanded },
            items = listOf("최신순", "오래된순", "별점순", "인기순"),
            selectedIndex = selectedIndex,
            onItemSelected = { selectedIndex = it }
        )
    }
}

