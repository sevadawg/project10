package com.app.project10.data.dto.game

import kotlinx.serialization.Serializable

@Serializable
data class Arena(
    val name: String? = "",
    val city: String? = "",
    val state: String? = "",
    val country: String? = ""
)
