package com.app.project10.ui.components.state

import androidx.compose.runtime.Composable

@Composable
fun Content(content: @Composable () -> Unit) {
    content()
}