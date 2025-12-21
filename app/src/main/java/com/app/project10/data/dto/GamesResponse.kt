package com.app.project10.data.dto

import kotlinx.serialization.Serializable

@Serializable
data class GamesResponse(
    val response: List<Game>
)
