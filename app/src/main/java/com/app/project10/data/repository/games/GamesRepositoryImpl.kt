package com.app.project10.data.repository.games

import android.annotation.SuppressLint
import com.app.project10.data.dto.game.Game

import com.app.project10.network.services.games.GamesNetworkService

class GamesRepositoryImpl(private val gamesService: GamesNetworkService) :
    GamesRepository {
    @SuppressLint("BuildListAdds")
    override suspend fun getGames(date: String): List<Game> =
        buildList {
            val response = gamesService.getGames(date)
            return response.body()?.response ?: emptyList()
        }
}