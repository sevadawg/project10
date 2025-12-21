package com.app.project10.data.repository

import com.app.project10.data.models.Game


interface TodayGamesRepository {
    suspend fun getTodayGames(todayDate: String): List<Game>
}