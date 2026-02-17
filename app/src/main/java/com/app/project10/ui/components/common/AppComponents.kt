package com.app.project10.ui.components.common

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardColors
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.app.project10.ui.theme.Dimens

@Composable
fun AppCard(
    modifier: Modifier = Modifier,
    onClick: (() -> Unit)? = null,
    colors: CardColors = CardDefaults.cardColors(),
    content: @Composable () -> Unit
) {
    val dimens = Dimens.current
    if (onClick == null) {
        Card(
            modifier = modifier,
            shape = RoundedCornerShape(dimens.cardCorner),
            colors = colors,
            elevation = CardDefaults.cardElevation(defaultElevation = dimens.cardElevation),
            content = { content() }
        )
    } else {
        Card(
            modifier = modifier,
            shape = RoundedCornerShape(dimens.cardCorner),
            onClick = onClick,
            colors = colors,
            elevation = CardDefaults.cardElevation(defaultElevation = dimens.cardElevation),
            content = { content() }
        )
    }
}

@Composable
fun AppSectionTitle(
    title: String,
    modifier: Modifier = Modifier
) {
    Text(
        text = title,
        modifier = modifier,
        style = MaterialTheme.typography.titleMedium
    )
}

@Composable
fun AppCenteredError(
    message: String,
    onRefresh: () -> Unit,
    modifier: Modifier = Modifier
) {
    val dimens = Dimens.current
    Column(
        modifier = modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(text = message, style = MaterialTheme.typography.bodyLarge)
        Button(
            onClick = onRefresh,
            modifier = Modifier.padding(top = dimens.md)
        ) {
            Text(text = "Refresh")
        }
    }
}

@Composable
fun AppCenteredLoading(modifier: Modifier = Modifier) {
    Column(
        modifier = modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        CircularProgressIndicator()
    }
}

