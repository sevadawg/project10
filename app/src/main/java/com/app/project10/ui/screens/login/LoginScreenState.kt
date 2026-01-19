package com.app.project10.ui.screens.login

sealed interface LoginScreenState {
    object Idle : LoginScreenState
    object Loading : LoginScreenState
    data class Success(val token: String) : LoginScreenState
    data class Error(val message: String) : LoginScreenState
}