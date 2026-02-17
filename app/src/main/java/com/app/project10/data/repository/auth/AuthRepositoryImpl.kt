package com.app.project10.data.repository.auth

import com.app.project10.data.repository.userpreferences.UserPreferencesRepository

class AuthRepositoryImpl(
    private val userPreferencesRepository: UserPreferencesRepository
) : AuthRepository {

    override suspend fun login(user: String, pass: String): Boolean {
        return true
    }

    override suspend fun clearToken() {
        userPreferencesRepository.clearAuthToken()
    }
}

