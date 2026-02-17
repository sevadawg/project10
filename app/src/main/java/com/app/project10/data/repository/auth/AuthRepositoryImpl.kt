package com.app.project10.data.repository.auth

import com.app.project10.data.local.preferences.UserPreferencesRepository
import com.app.project10.data.remote.api.auth.AuthApiService
import com.app.project10.domain.repository.AuthRepository
import timber.log.Timber
import kotlin.coroutines.cancellation.CancellationException

class AuthRepositoryImpl(
    private val authApiService: AuthApiService,
    private val userPreferencesRepository: UserPreferencesRepository
) : AuthRepository {

    override suspend fun login(user: String, pass: String): Boolean {
        return true
    }

    override suspend fun validateToken(): Boolean {
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
        } catch (e: CancellationException) {
            throw e
        } catch (e: Exception) {
            Timber.e(e, "Token validation failed due to a network error.")
            false
        }
    }

    override suspend fun clearToken() {
        userPreferencesRepository.clearAuthToken()
    }
}


