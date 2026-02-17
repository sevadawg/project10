package com.app.project10.domain.repository

interface AuthRepository {
    suspend fun login(user: String, pass: String): Boolean
    suspend fun validateToken(): Boolean
    suspend fun clearToken()
}


