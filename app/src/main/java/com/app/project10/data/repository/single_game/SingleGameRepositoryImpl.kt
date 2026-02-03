package com.app.project10.data.repository.single_game

import com.app.project10.data.dto.game.GamesResponse
import com.app.project10.network.services.single_game.SingleGameNetworkService

class SingleGameRepositoryImpl(private val singleGameNetworkService: SingleGameNetworkService) : SingleGameRepository {
    override suspend fun getGame(id: Int): GamesResponse {
        val response = singleGameNetworkService.getGame(id)
        return response.body() ?: throw Exception("Game not found")
    }
}

