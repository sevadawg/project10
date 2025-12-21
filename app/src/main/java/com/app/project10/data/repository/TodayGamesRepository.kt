package com.app.project10.data.repository

import com.app.project10.data.models.GamesResponse
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow


interface TodayGamesRepository {
    suspend fun getTodayGames(todayDate: String): List<GamesResponse>
}