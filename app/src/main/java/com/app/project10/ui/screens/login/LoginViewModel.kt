package com.app.project10.ui.screens.login

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.app.project10.data.repository.login.LoginRepository
import com.app.project10.data.repository.user_preferences.UserPreferencesRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlin.coroutines.cancellation.CancellationException

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

    private val _uiState = MutableStateFlow<LoginScreenState>(LoginScreenState.Idle)
    val uiState = _uiState.asStateFlow()

    fun onGoogleSignInSucceeded(idToken: String) {
        viewModelScope.launch {
            _uiState.value = LoginScreenState.Loading
            try {
                val response = loginRepository.login(idToken)
                val jwt = response.jwt ?: error("JWT missing from backend")
                userPreferencesRepository.saveAuthToken(jwt)
                _uiState.value = LoginScreenState.Success
            } catch (e: Exception) {
                if (e is CancellationException) throw e
                _uiState.value = LoginScreenState.Error(e.message ?: "Login failed")
            }
        }
    }

    fun onDialogDismissed() {
        _uiState.value = LoginScreenState.Idle
    }
}
