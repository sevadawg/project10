package com.app.project10.data.repository.singlegame

import com.app.project10.data.dto.game.GamesResponse
import com.app.project10.network.services.singlegame.SingleGameNetworkService

class SingleGameRepositoryImpl(private val singleGameNetworkService: SingleGameNetworkService) : SingleGameRepository {
    override suspend fun getGame(id: Int): GamesResponse {
        val response = singleGameNetworkService.getGame(id)
        return response.body() ?: throw IllegalStateException("Game not found for id=$id")
    }
}


