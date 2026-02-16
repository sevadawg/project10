package com.app.project10.ui

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.getValue
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.app.project10.data.auth.AuthState
import com.app.project10.ui.navigation.Navigation
import com.app.project10.ui.theme.Project10Theme
import org.koin.androidx.viewmodel.ext.android.viewModel

class MainActivity : ComponentActivity() {

    private val authViewModel: AuthViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val splashScreen = installSplashScreen()
        splashScreen.setKeepOnScreenCondition {
            authViewModel.state.value == AuthState.Loading
        }

        enableEdgeToEdge()
        setContent {
            Project10Theme {
                val state by authViewModel.state.collectAsStateWithLifecycle()
                Navigation(
                    authState = state,
                    onLoginSuccess = authViewModel::validateToken
                )
            }
        }
    }
}
