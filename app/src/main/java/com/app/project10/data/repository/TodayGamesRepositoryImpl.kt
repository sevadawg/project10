package com.app.project10.data.repository

import android.annotation.SuppressLint
import com.app.project10.data.models.GamesResponse
import com.app.project10.network.services.GamesNetworkService

class TodayGamesRepositoryImpl(private val gamesNetworkService: GamesNetworkService) :
    TodayGamesRepository {
    @SuppressLint("BuildListAdds")
    override suspend fun getTodayGames(todayDate: String): List<GamesResponse> =
        buildList {
            val response = gamesNetworkService.getTodayGames(todayDate)
            return response.body()?.response ?: emptyList()
        }
}