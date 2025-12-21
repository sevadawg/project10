package com.app.project10.data.dto

import kotlinx.serialization.Serializable

@Serializable
data class TeamDetails(
    val id: Int = 0,
    val name: String? = "",
    val nickname: String? = "",
    val code: String? = "",
    val logo: String? = ""
)
