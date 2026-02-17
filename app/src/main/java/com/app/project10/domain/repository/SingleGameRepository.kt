package com.app.project10.domain.repository

import com.app.project10.data.remote.dto.game.GamesResponse

interface SingleGameRepository {
    suspend fun getGame(id: Int): GamesResponse
}


