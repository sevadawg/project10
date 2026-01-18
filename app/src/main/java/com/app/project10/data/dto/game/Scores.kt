package com.app.project10.data.dto.game

import kotlinx.serialization.Serializable

@Serializable
data class Scores(
    val visitors: ScoresDetails,
    val home: ScoresDetails
)
