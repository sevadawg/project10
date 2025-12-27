package com.app.project10.data.repository.single_game

import com.app.project10.data.dto.Game
import com.app.project10.network.services.single_game.SingleGameNetworkService

class SingleGameRepositoryImpl(private val singleGameNetworkService: SingleGameNetworkService) : SingleGameRepository {
    override suspend fun getGame(id: Int): Game {
        val response = singleGameNetworkService.getGame(id)
        return response.body() ?: throw Exception("Game not found")
    }
}

