package com.app.project10.domain.repository

import com.app.project10.data.remote.dto.login.LoginResponse

interface LoginRepository {
    suspend fun login(idToken: String): LoginResponse
}


