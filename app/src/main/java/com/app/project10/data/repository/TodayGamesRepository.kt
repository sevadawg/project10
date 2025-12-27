package com.app.project10.data.repository

import com.app.project10.data.dto.Game



interface GamesRepository {
    suspend fun getGames(date: String): List<Game>
}