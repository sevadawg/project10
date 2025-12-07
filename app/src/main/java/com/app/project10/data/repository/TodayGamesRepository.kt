package com.app.project10.data.repository

import com.app.project10.data.models.GamesResponse
import com.app.project10.network.base.Error
import com.app.project10.network.base.Result


interface TodayGamesRepository {
    suspend fun getTodayGames(todayDate: String): Result<List<GamesResponse>, Error>
}