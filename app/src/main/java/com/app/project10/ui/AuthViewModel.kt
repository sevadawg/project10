package com.app.project10.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.app.project10.core.state.flowUiState
import com.app.project10.data.auth.AuthState
import com.app.project10.data.repository.auth.AuthRepository
import kotlinx.coroutines.flow.StateFlow
import timber.log.Timber

class AuthViewModel(
    private val authRepository: AuthRepository
) : ViewModel() {

    private sealed interface AuthIntent {
        data class Validate(val triggerId: Long) : AuthIntent
        data class Login(val user: String, val pass: String) : AuthIntent
        data object Logout : AuthIntent
    }

    private val authState = flowUiState<AuthIntent, AuthState>(
        scope = viewModelScope,
        initialInput = AuthIntent.Validate(triggerId = 0L)
    ) {
        initial { AuthState.Loading }

        fetch { intent ->
            when (intent) {
                is AuthIntent.Validate -> {
                    val isTokenValid = authRepository.validateToken()
                    if (isTokenValid) AuthState.Authenticated else AuthState.Unauthenticated
                }

                is AuthIntent.Login -> {
                    val loginSuccessful = authRepository.login(intent.user, intent.pass)
                    if (loginSuccessful) AuthState.Authenticated else AuthState.Unauthenticated
                }

                AuthIntent.Logout -> {
                    authRepository.clearToken()
                    AuthState.Unauthenticated
                }
            }
        }

        onError { e ->
            Timber.e(e, "Auth flow failed")
            AuthState.Unauthenticated
        }
    }

    val state: StateFlow<AuthState> = authState.state

    fun validateToken() {
        authState.update(AuthIntent.Validate(triggerId = System.nanoTime()))
    }

    fun login(user: String, pass: String) {
        authState.update(AuthIntent.Login(user, pass))
    }

    fun logout() {
        authState.update(AuthIntent.Logout)
    }
}
