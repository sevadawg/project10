package com.app.project10.data.repository.single_game

import com.app.project10.data.dto.Game

interface SingleGameRepository {
   suspend fun getGame(id: Int): Game
}
