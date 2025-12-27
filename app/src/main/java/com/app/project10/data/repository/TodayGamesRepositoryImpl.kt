package com.app.project10.data.repository

import android.annotation.SuppressLint
import com.app.project10.data.dto.Game

import com.app.project10.network.services.GamesNetworkService

class GamesRepositoryImpl(private val gamesNetworkService: GamesNetworkService) :
    GamesRepository {
    @SuppressLint("BuildListAdds")
    override suspend fun getGames(date: String): List<Game> =
        buildList {
            val response = gamesNetworkService.getGames(date)
            return response.body()?.response ?: emptyList()
        }
}