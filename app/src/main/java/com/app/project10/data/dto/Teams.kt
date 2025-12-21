package com.app.project10.data.dto

import kotlinx.serialization.Serializable

@Serializable
data class Teams(
    val visitors: TeamDetails,
    val home: TeamDetails
)
