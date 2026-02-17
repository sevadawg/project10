package com.app.project10.network.services.login

import com.app.project10.data.dto.login.GoogleLoginRequest
import com.app.project10.data.dto.login.LoginResponse
import retrofit2.http.Body
import retrofit2.http.POST

interface LoginService {
    @POST("/auth/google")
    suspend fun login(@Body request: GoogleLoginRequest): LoginResponse
}

