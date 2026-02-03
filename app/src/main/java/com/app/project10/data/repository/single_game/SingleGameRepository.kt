package com.app.project10.data.repository.single_game

import com.app.project10.data.dto.game.GamesResponse

interface SingleGameRepository {
   suspend fun getGame(id: Int): GamesResponse
}
