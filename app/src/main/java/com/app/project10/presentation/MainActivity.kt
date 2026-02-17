package com.app.project10.presentation

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.getValue
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.app.project10.presentation.navigation.Navigation
import com.app.project10.presentation.theme.Project10Theme
import org.koin.androidx.viewmodel.ext.android.viewModel

class MainActivity : ComponentActivity() {

    private val authViewModel: AuthViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        enableEdgeToEdge()
        setContent {
            val authState by authViewModel.state.collectAsStateWithLifecycle()

            Project10Theme {
                Navigation(
                    authState = authState,
                    onLoginSuccess = authViewModel::validateToken
                )
            }
        }
    }
}


