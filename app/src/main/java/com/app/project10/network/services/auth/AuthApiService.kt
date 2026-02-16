package com.app.project10.network.services.auth

import com.app.project10.data.dto.auth.TokenValidationResponse
import retrofit2.http.GET

interface AuthApiService {
    @GET("/auth/verify")
    suspend fun verifyToken(): TokenValidationResponse
}