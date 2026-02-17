package com.app.project10.data.repository.games

import com.app.project10.data.dto.game.Game
import com.app.project10.network.services.games.GamesNetworkService

class GamesRepositoryImpl(
    private val gamesService: GamesNetworkService
) : GamesRepository {
    override suspend fun getGames(date: String): List<Game> {
        return gamesService.getGames(date).body()?.response.orEmpty()
    }
}

