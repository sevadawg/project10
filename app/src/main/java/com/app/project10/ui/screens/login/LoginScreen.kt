package com.app.project10.ui.screens.login

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import com.app.project10.R
import com.app.project10.ui.theme.Dimens

@Composable
fun LoginScreen(
    onSignInClick: () -> Unit
) {
    Surface(
        modifier = Modifier.fillMaxSize(),
        color = MaterialTheme.colorScheme.background
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(Dimens.PaddingNormal),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = "Welcome Back",
                style = MaterialTheme.typography.headlineLarge,
                fontWeight = FontWeight.Bold
            )
            Text(
                text = "Sign in to continue",
                style = MaterialTheme.typography.bodyLarge,
                color = Color.Gray
            )
            Spacer(modifier = Modifier.height(Dimens.SpacerLarge))
            GoogleSignInButton(
                onClick = onSignInClick
            )
        }
    }
}

@Composable
private fun GoogleSignInButton(
    onClick: () -> Unit
) {
    Button(
        onClick = onClick,
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = Dimens.ButtonPaddingHorizontal),
        colors = ButtonDefaults.buttonColors(
            containerColor = Color.White,
            contentColor = Color.Black
        ),
        elevation = ButtonDefaults.buttonElevation(
            defaultElevation = Dimens.ButtonElevation
        )
    ) {
        Icon(
            painter = painterResource(id = R.drawable.ic_1),
            contentDescription = "Google sign-in button",
            modifier = Modifier.size(Dimens.IconSize),
            tint = Color.Unspecified // Important to keep original colors of the logo
        )
        Text(
            text = "Sign in with Google",
            modifier = Modifier.padding(start = Dimens.IconPadding),
            fontWeight = FontWeight.Medium
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun LoginScreenPreview() {
    LoginScreen(onSignInClick = {})
}
