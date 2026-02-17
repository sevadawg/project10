package com.app.project10.presentation.components.state

import androidx.compose.runtime.Composable
import com.app.project10.presentation.components.common.AppCenteredError

@Composable
fun Error(onRefresh: () -> Unit) {
    AppCenteredError(message = "Something went wrong", onRefresh = onRefresh)
}


