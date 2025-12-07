package com.app.project10.data.repository

import com.app.project10.data.models.GamesResponse
import kotlinx.coroutines.flow.Flow


interface TodayGamesRepository {
    fun getTodayGames(todayDate: String): Flow<List<GamesResponse>>
}