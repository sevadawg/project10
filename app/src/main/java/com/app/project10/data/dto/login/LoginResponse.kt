package com.app.project10.data.dto.login

data class LoginResponse(
    val userId: String,
    val email: String,
    val jwt: String? = null
)