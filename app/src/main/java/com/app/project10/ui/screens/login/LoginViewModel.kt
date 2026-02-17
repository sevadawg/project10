package com.app.project10.ui.screens.login

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.app.project10.core.state.flowUiState
import com.app.project10.data.repository.login.LoginRepository
import com.app.project10.data.repository.userpreferences.UserPreferencesRepository
import kotlinx.coroutines.flow.StateFlow
import timber.log.Timber

sealed interface LoginScreenState {
    object Idle : LoginScreenState
    object Loading : LoginScreenState
    data object Success : LoginScreenState
    data class Error(val message: String) : LoginScreenState
}

class LoginViewModel(
    private val loginRepository: LoginRepository,
    private val userPreferencesRepository: UserPreferencesRepository
) : ViewModel() {

    private val loginState = flowUiState(
        scope = viewModelScope,
        initialInput = "",
        builder = {
            initial { LoginScreenState.Idle }

            fetch { idToken ->
                if (idToken.isBlank()) {
                    LoginScreenState.Idle
                } else {
                    val response = loginRepository.login(idToken)
                    val jwt = response.jwt?.trim().orEmpty()
                    if (jwt.isBlank()) {
                        throw IllegalStateException("Empty JWT token received from login API")
                    }
                    userPreferencesRepository.saveAuthToken(jwt)
                    LoginScreenState.Success
                }
            }

            onError { e ->
                Timber.e(e)
                LoginScreenState.Error("Login failed")
            }
        }
    )


    val state: StateFlow<LoginScreenState> = loginState.state

    fun onGoogleSignInSucceeded(idToken: String) {
        loginState.update(idToken)
    }
}

