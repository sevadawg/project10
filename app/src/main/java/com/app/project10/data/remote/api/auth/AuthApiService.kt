package com.app.project10.data.remote.api.auth

import com.app.project10.data.remote.dto.auth.TokenValidationResponse
import retrofit2.http.GET

interface AuthApiService {
    @GET("/auth/verify")
    suspend fun verifyToken(): TokenValidationResponse
}

