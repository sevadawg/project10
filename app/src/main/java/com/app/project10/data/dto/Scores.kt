package com.app.project10.data.dto

import kotlinx.serialization.Serializable

@Serializable
data class Scores(
    val visitors: ScoresDetails,
    val home: ScoresDetails
)
