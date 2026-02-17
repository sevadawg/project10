package com.app.project10.data.repository.games

import com.app.project10.data.remote.api.games.GamesNetworkService
import com.app.project10.data.remote.dto.game.Game
import com.app.project10.domain.repository.GamesRepository

class GamesRepositoryImpl(
    private val gamesService: GamesNetworkService
) : GamesRepository {
    override suspend fun getGames(date: String): List<Game> {
        return gamesService.getGames(date).body()?.response.orEmpty()
    }
}


