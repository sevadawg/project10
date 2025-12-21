package com.app.project10.data.dto

import kotlinx.serialization.Serializable

@Serializable
data class Date(
    val start: String? = "",
    val end: String? = "",
    val duration: String? = ""
)
