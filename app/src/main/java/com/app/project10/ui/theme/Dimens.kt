package com.app.project10.ui.theme

import androidx.compose.runtime.Composable
import androidx.compose.runtime.ReadOnlyComposable
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

data class AppDimens(
    val xxs: Dp = 4.dp,
    val xs: Dp = 8.dp,
    val sm: Dp = 12.dp,
    val md: Dp = 16.dp,
    val lg: Dp = 24.dp,
    val xl: Dp = 32.dp,
    val xxl: Dp = 48.dp,
    val cardCorner: Dp = 16.dp,
    val cardElevation: Dp = 2.dp,
    val buttonElevation: Dp = 2.dp,
    val icon: Dp = 24.dp,
    val listCardHeight: Dp = 110.dp,
    val teamRowHeight: Dp = 26.dp,
    val navBarHeight: Dp = 80.dp,
    val calendarHeaderHeight: Dp = 48.dp,
    val calendarNavButton: Dp = 32.dp,
    val calendarNavIcon: Dp = 16.dp,
    val calendarDayWidth: Dp = 35.dp,
    val calendarDayHeight: Dp = 48.dp
)

internal val LocalDimens = staticCompositionLocalOf { AppDimens() }

object Dimens {
    val current: AppDimens
        @Composable
        @ReadOnlyComposable
        get() = LocalDimens.current
}
