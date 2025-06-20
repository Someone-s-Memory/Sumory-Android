package com.sumory.design_system.icon

import android.opengl.Visibility
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.sumory.design_system.R

@Composable
fun CalendarIcon(
    modifier: Modifier = Modifier,
    tint: Color = Color.Unspecified
){
    Icon(
        painter = painterResource(id = R.drawable.ic_calendar),
        contentDescription = stringResource(id = R.string.calendar_description),
        modifier = modifier,
        tint = tint
    )
}
@Composable
fun DiaryIcon(
    modifier: Modifier = Modifier,
    tint: Color = Color.Unspecified
){
    Icon(
        painter = painterResource(id = R.drawable.ic_diary),
        contentDescription = stringResource(id = R.string.diary_description),
        modifier = modifier,
        tint = tint
    )
}

@Composable
fun HomeIcon(
    modifier: Modifier = Modifier,
    tint: Color = Color.Unspecified
){
    Icon(
        painter = painterResource(id = R.drawable.ic_home),
        contentDescription = stringResource(id = R.string.home_description),
        modifier = modifier,
        tint = tint
    )
}

@Composable
fun ProfileIcon(
    modifier: Modifier = Modifier,
    tint: Color = Color.Unspecified
){
    Icon(
        painter = painterResource(id = R.drawable.ic_profile),
        contentDescription = stringResource(id = R.string.profile_description),
        modifier = modifier,
        tint = tint
    )
}

@Composable
fun SettingIcon(
    modifier: Modifier = Modifier,
    tint: Color = Color.Unspecified
){
    Icon(
        painter = painterResource(id = R.drawable.ic_setting),
        contentDescription = stringResource(id = R.string.setting_description),
        modifier = modifier,
        tint = tint
    )
}

@Composable
fun StatIcon(
    modifier: Modifier = Modifier,
    tint: Color = Color.Unspecified
){
    Icon(
        painter = painterResource(id = R.drawable.ic_stat),
        contentDescription = stringResource(id = R.string.stat_description),
        modifier = modifier,
        tint = tint
    )
}

@Composable
fun CoinIcon(
    modifier: Modifier = Modifier,
    tint: Color = Color.Unspecified
){
    Icon(
        painter = painterResource(id = R.drawable.ic_coin),
        contentDescription = stringResource(id = R.string.coin_description),
        modifier = modifier,
        tint = tint
    )
}

@Composable
fun SearchIcon(
    modifier: Modifier = Modifier,
    tint: Color = Color.Unspecified
){
    Icon(
        painter = painterResource(id = R.drawable.ic_search),
        contentDescription = stringResource(id = R.string.search_description),
        modifier = modifier,
        tint = tint
    )
}

@Composable
fun FilterIcon(
    modifier: Modifier = Modifier,
    tint: Color = Color.Unspecified
){
    Icon(
        painter = painterResource(id = R.drawable.ic_filter),
        contentDescription = stringResource(id = R.string.filter_description),
        modifier = modifier,
        tint = tint
    )
}

@Composable
fun StarIcon(
    modifier: Modifier = Modifier,
    tint: Color = Color.Unspecified
){
    Icon(
        painter = painterResource(id = R.drawable.ic_star),
        contentDescription = stringResource(id = R.string.star_description),
        modifier = modifier,
        tint = tint
    )
}

@Composable
fun LeftArrowIcon(
    modifier: Modifier = Modifier,
    tint: Color = Color.Unspecified
){
    Icon(
        painter = painterResource(id = R.drawable.ic_arrow_left),
        contentDescription = stringResource(id = R.string.left_arrow_description),
        modifier = modifier,
        tint = tint
    )
}

@Composable
fun RightArrowIcon(
    modifier: Modifier = Modifier,
    tint: Color = Color.Unspecified
){
    Icon(
        painter = painterResource(id = R.drawable.ic_arrow_right),
        contentDescription = stringResource(id =R.string.right_arrow_description),
        modifier = modifier,
        tint = tint
    )
}

@Composable
fun AddIcon(
    modifier: Modifier = Modifier,
    tint: Color = Color.Unspecified
){
    Icon(
        painter = painterResource(id = R.drawable.ic_add),
        contentDescription = stringResource(id =R.string.add_description),
        modifier = modifier,
        tint = tint
    )
}

@Composable
fun EyeIcon(
    modifier: Modifier = Modifier,
    isSelected: Boolean = false,
    tint: Color = Color.Unspecified,
) {
    Icon(
        painter = if (isSelected) painterResource(id = R.drawable.ic_visibility)
        else painterResource(id = R.drawable.ic_visibility_off),
        contentDescription = stringResource(id = R.string.visibility_description),
        modifier = modifier,
        tint = tint
    )
}