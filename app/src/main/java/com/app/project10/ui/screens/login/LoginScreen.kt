package com.app.project10.ui.screens.login

import android.util.Log
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
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.credentials.CredentialManager
import androidx.credentials.CustomCredential
import androidx.credentials.GetCredentialRequest
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.app.project10.R
import com.app.project10.ui.theme.Dimens
import com.google.android.libraries.identity.googleid.GetGoogleIdOption
import com.google.android.libraries.identity.googleid.GoogleIdTokenCredential
import kotlinx.coroutines.launch
import org.koin.androidx.compose.koinViewModel

private const val TAG = "LoginScreen"

@Composable
fun LoginScreen(
    viewModel: LoginViewModel = koinViewModel(),
    onLoginSuccess: (String) -> Unit
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    val context = LocalContext.current
    val coroutineScope = rememberCoroutineScope()
    val credentialManager = remember { CredentialManager.create(context) }


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
            val onSignInClick: () -> Unit = {
                coroutineScope.launch {
                    try {
                        // 1. Create a GetGoogleIdOption
                        val googleIdOption = GetGoogleIdOption.Builder()
                            .setFilterByAuthorizedAccounts(false)
                            .setServerClientId("526082133202-euqfmdtsk07gq9688e9pcn1gqckmd6nu.apps.googleusercontent.com") // Must be a web client ID
                            .build()

                        // 2. Create a GetCredentialRequest
                        val request = GetCredentialRequest.Builder()
                            .addCredentialOption(googleIdOption)
                            .build()

                        // 3. Call the Credential Manager
                        val result = credentialManager.getCredential(context = context, request = request)
                        val credential = result.credential

                        // 4. Handle the result
                        if (credential is CustomCredential && credential.type == GoogleIdTokenCredential.TYPE_GOOGLE_ID_TOKEN_CREDENTIAL) {
                            val idToken = GoogleIdTokenCredential.createFrom(credential.data).idToken

                            viewModel.onGoogleSignInSucceeded(idToken)
                        } else {
                            Log.w(TAG, "Google Sign In failed: Unexpected credential type")
                        }

                    } catch (e: Exception) {
                        Log.w(TAG, "Google sign in failed", e)
                    }
                }
            }


            when (val state = uiState) {
                is LoginScreenState.Idle -> {
                    LoginContent(onSignInClick = onSignInClick)
                }
                is LoginScreenState.Loading -> {
                    CircularProgressIndicator()
                }
                is LoginScreenState.Success -> {
                    onLoginSuccess(state.token)
                }
                is LoginScreenState.Error -> {
                    LoginContent(onSignInClick = onSignInClick)
                    Spacer(modifier = Modifier.height(Dimens.SpacerNormal))
                    Text(
                        text = state.message,
                        color = MaterialTheme.colorScheme.error
                    )
                }
            }
        }
    }
}

@Composable
private fun LoginContent(onSignInClick: () -> Unit) {
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

@Composable
private fun GoogleSignInButton(
    onClick: () -> Unit
) {
    Button(
        onClick = onClick,
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = Dimens.PaddingHorizontal),
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
    LoginContent(onSignInClick = {})
}
