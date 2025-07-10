package com.sumory.design_system.component.navigation

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsPressedAsState
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.sumory.design_system.icon.InventoryIcon
import com.sumory.design_system.icon.SettingIcon
import com.sumory.design_system.icon.StoreIcon
import com.sumory.design_system.theme.SumoryTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SumoryTopBar(
    modifier: Modifier = Modifier,
    title: String,
    coinCount: Int,
    currentRoute: String?,
    onNavigateTo: (String) -> Unit,
) {
    SumoryTheme { colors, typography ->
        Column {
            TopAppBar(
                title = {
                    Text(text = title, style = typography.titleRegular2)
                },
                actions = {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Text(
                            text = "💰 $coinCount",
                            modifier = modifier
                                .padding(horizontal = 8.dp)
                                .clickable(
                                    indication = null,
                                    interactionSource = remember { MutableInteractionSource() }
                                ) { onNavigateTo("storeRoute") },
                            style = typography.captionRegular1
                        )

                        // StoreIcon with pressed alpha
                        val storeSource = remember { MutableInteractionSource() }
                        val storePressed by storeSource.collectIsPressedAsState()
                        StoreIcon(
                            modifier = modifier
                                .clickable(
                                    indication = null,
                                    interactionSource = storeSource
                                ) { onNavigateTo("storeRoute") }
                                .padding(horizontal = 5.dp)
                                .alpha(if (storePressed) 0.6f else 1f),
                            tint = colors.black
                        )

                        // InventoryIcon with pressed alpha
                        val inventorySource = remember { MutableInteractionSource() }
                        val inventoryPressed by inventorySource.collectIsPressedAsState()
                        InventoryIcon(
                            modifier = modifier
                                .clickable(
                                    indication = null,
                                    interactionSource = inventorySource
                                ) { onNavigateTo("storeRoute") }
                                .padding(horizontal = 5.dp)
                                .alpha(if (inventoryPressed) 0.6f else 1f),
                            tint = colors.black
                        )

                        // SettingIcon with pressed alpha
                        val settingSource = remember { MutableInteractionSource() }
                        val settingPressed by settingSource.collectIsPressedAsState()
                        SettingIcon(
                            modifier = modifier
                                .clickable(
                                    indication = null,
                                    interactionSource = settingSource
                                ) { onNavigateTo("settingRoute") }
                                .padding(start = 5.dp, end = 10.dp)
                                .alpha(if (settingPressed) 0.6f else 1f),
                            tint = colors.black
                        )
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = colors.gray50
                )
            )
            Divider(
                color = colors.gray100,
                thickness = 1.dp
            )
        }
    }
}

@Preview
@Composable
private fun SumoryTopBarPreview() {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
    ) {
        SumoryTopBar(
            title = "Sumory",
            coinCount = 150,
            currentRoute = "",
            onNavigateTo = {  },
        )
    }
}