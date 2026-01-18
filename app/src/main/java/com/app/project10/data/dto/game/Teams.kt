package com.app.project10.data.dto.game

import kotlinx.serialization.Serializable

@Serializable
data class Teams(
    val visitors: TeamDetails,
    val home: TeamDetails
)
