package com.app.project10.data.repository

import android.annotation.SuppressLint
import com.app.project10.data.dto.Game

import com.app.project10.network.services.GamesNetworkService

class TodayGamesRepositoryImpl(private val gamesNetworkService: GamesNetworkService) :
    TodayGamesRepository {
    @SuppressLint("BuildListAdds")
    override suspend fun getTodayGames(todayDate: String): List<Game> =
        buildList {
            val response = gamesNetworkService.getTodayGames(todayDate)
            return response.body()?.response ?: emptyList()
        }
}