package com.sumory.design_system.component.navigation

import androidx.compose.foundation.interaction.Interaction
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.RowScope
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.sumory.design_system.R
import com.sumory.design_system.theme.SumoryTheme
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.emptyFlow

internal object NoRippleInteractionSource : MutableInteractionSource {
    override val interactions: Flow<Interaction> = emptyFlow()

    override suspend fun emit(interaction: Interaction) = Unit

    override fun tryEmit(interaction: Interaction): Boolean = true
}

@Composable
fun RowScope.SumoryNavigationBarItem(
    modifier: Modifier = Modifier,
    selected: Boolean,
    onClick: () -> Unit,
    icon: @Composable () -> Unit,
    selectedIcon: @Composable () -> Unit = icon,
    label: @Composable () -> Unit,
) {
    SumoryTheme { colors, _ ->
        NavigationBarItem(
            modifier = modifier,
            selected = selected,
            onClick = onClick,
            icon = if (selected) selectedIcon else icon,
            label = label,
            alwaysShowLabel = false,
            colors = NavigationBarItemDefaults.colors(
                selectedIconColor = colors.main,
                unselectedIconColor = colors.gray500,
                selectedTextColor = colors.main,
                unselectedTextColor = colors.gray500,
                indicatorColor = colors.gray50
            ),
            interactionSource = NoRippleInteractionSource
        )
    }
}

@Composable
fun SumoryNavigationBar(
    modifier: Modifier = Modifier,
    content: @Composable RowScope.() -> Unit
) {
    SumoryTheme { colors, _ ->
        Column {
            Divider(
                thickness = 1.dp,
                color = colors.gray100
            )
            NavigationBar(
                modifier = modifier,
                containerColor = colors.gray50,
                contentColor = colors.gray500,
                tonalElevation = 0.dp,
                content = content
            )
        }
    }
}

@Preview
@Composable
private fun SumoryNavigationPreview() {
    val items = listOf(
        "캘린더",
        "모아보기",
        "홈",
        "통계",
        "프로필",
    )
    val icons = listOf(
        R.drawable.ic_calendar,
        R.drawable.ic_diary,
        R.drawable.ic_home,
        R.drawable.ic_stat,
        R.drawable.ic_profile,
    )
    SumoryTheme { colors, typography ->
        SumoryNavigationBar {
            items.forEachIndexed { index, item ->
                SumoryNavigationBarItem(
                    icon = {
                        Icon(
                            painter = painterResource(id = icons[index]),
                            contentDescription = item
                        )
                    },
                    selectedIcon = {
                        Icon(
                            painter = painterResource(id = icons[index]),
                            contentDescription = item,
                            tint = colors.main
                        )
                    },
                    label = {
                        Text(
                            text = item,
                            style = typography.captionRegular2
                        )
                    },
                    selected = index == 0,
                    onClick = {},
                )
            }
        }
    }
}
