package com.app.project10.data.repository.auth

interface AuthRepository {
    suspend fun login(user: String, pass: String): Boolean
    suspend fun clearToken()
    suspend fun validateToken(): Boolean
}