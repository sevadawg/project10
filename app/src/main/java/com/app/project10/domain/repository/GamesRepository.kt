package com.app.project10.domain.repository

import com.app.project10.data.remote.dto.game.Game


interface GamesRepository {
    suspend fun getGames(date: String): List<Game>
}

