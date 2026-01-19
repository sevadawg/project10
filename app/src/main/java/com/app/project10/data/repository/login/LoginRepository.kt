package com.app.project10.data.repository.login

import com.app.project10.data.dto.login.LoginResponse

interface LoginRepository {
    suspend fun login(idToken: String): LoginResponse
}
