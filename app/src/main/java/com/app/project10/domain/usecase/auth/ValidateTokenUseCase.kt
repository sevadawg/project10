package com.app.project10.domain.usecase.auth

import com.app.project10.data.repository.userpreferences.UserPreferencesRepository
import com.app.project10.network.services.auth.AuthApiService
import timber.log.Timber

class ValidateTokenUseCase(
    private val authApiService: AuthApiService,
    private val userPreferencesRepository: UserPreferencesRepository
) {
    suspend operator fun invoke(): Boolean {
        val token = userPreferencesRepository.getToken()

        if (token.isNullOrEmpty()) {
            Timber.d("No token found in local storage.")
            return false
        }

        return try {
            val isTokenValid = authApiService.verifyToken().isValid

            if (isTokenValid) {
                Timber.d("Token is valid.")
                true
            } else {
                Timber.w("Token is invalid or expired. Clearing it.")
                userPreferencesRepository.clearAuthToken()
                false
            }
        } catch (e: Exception) {
            Timber.e(e, "Token validation failed due to a network error.")
            false
        }
    }
}

