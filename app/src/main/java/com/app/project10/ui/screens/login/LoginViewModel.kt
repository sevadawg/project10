package com.app.project10.ui.screens.login

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.app.project10.data.repository.login.LoginRepository
import com.app.project10.data.repository.user_preferences.UserPreferencesRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

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
                val token = response.jwt ?: ""
                userPreferencesRepository.saveAuthToken(token)
                _uiState.value = LoginScreenState.Success(token)
            } catch (e: Exception) {
                _uiState.value = LoginScreenState.Error(e.message ?: "An unknown error occurred")
            }
        }
    }

    fun onDialogDismissed() {
        _uiState.value = LoginScreenState.Idle
    }
}
