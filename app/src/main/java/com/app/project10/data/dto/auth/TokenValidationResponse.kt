package com.app.project10.data.dto.auth

import kotlinx.serialization.Serializable

@Serializable
data class TokenValidationResponse(
    val isValid: Boolean
)