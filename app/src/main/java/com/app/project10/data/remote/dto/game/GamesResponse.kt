package com.app.project10.data.remote.dto.game


data class GamesResponse(
    val response: List<Game>
) {
    fun toGameResponse(): Game {
        return response[0]
    }
}


