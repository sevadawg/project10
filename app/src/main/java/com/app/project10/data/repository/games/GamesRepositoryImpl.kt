package com.app.project10.data.repository.games

import com.app.project10.data.remote.api.games.GamesNetworkService
import com.app.project10.data.remote.dto.game.Game
import com.app.project10.domain.repository.GamesRepository

class GamesRepositoryImpl(
    private val gamesService: GamesNetworkService
) : GamesRepository {
    override suspend fun getGames(date: String): List<Game> {
        val response = gamesService.getGames(date)

        if (!response.isSuccessful) {
            throw IllegalStateException("Failed to load games for $date: ${response.code()} ${response.message()}")
        }
        val body = response.body()
            ?: throw IllegalStateException("Empty response body for date=$date")
        return body.response
    }
}

