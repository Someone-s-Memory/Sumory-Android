package com.sumory.design_system.icon

import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
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