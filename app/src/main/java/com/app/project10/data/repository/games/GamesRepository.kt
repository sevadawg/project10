package com.app.project10.data.repository.games

import com.app.project10.data.dto.game.Game



interface GamesRepository {
    suspend fun getGames(date: String): List<Game>
}
