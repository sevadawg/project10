package com.app.project10.domain.usecase.auth

import com.app.project10.data.local.preferences.UserPreferencesRepository
import com.app.project10.domain.repository.AuthRepository
import timber.log.Timber

class ValidateTokenUseCase(
    private val authRepository: AuthRepository,
    private val userPreferencesRepository: UserPreferencesRepository
) {
    suspend operator fun invoke(): Boolean {
        val token = userPreferencesRepository.getToken()

        if (token.isNullOrEmpty()) {
            Timber.d("No token found in local storage.")
            return false
        }

        return authRepository.validateToken()
    }
}


