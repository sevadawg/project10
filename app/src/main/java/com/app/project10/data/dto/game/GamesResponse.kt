package com.app.project10.data.dto.game

import kotlinx.serialization.Serializable

@Serializable
data class GamesResponse(
    val response: List<Game>
) {
    fun toGameResponse(): Game {
        return response[0]
    }
}
