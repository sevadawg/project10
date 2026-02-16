package com.app.project10.data.repository.auth

import com.app.project10.data.repository.user_preferences.UserPreferencesRepository
import com.app.project10.network.services.auth.AuthApiService
import timber.log.Timber

class AuthRepositoryImpl(
    private val authApiService: AuthApiService,
    private val userPreferencesRepository: UserPreferencesRepository
) : AuthRepository {

    override suspend fun validateToken(): Boolean {
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
                clearToken()
                false
            }
        } catch (e: Exception) {
            Timber.e(e, "Token validation failed due to a network error.")
            false
        }
    }

    override suspend fun login(user: String, pass: String): Boolean {
        return true
    }

    override suspend fun clearToken() {
        userPreferencesRepository.clearAuthToken()
    }
}