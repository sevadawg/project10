package com.app.project10.data.repository.singlegame

import com.app.project10.data.remote.api.singlegame.SingleGameNetworkService
import com.app.project10.data.remote.dto.game.GamesResponse
import com.app.project10.domain.repository.SingleGameRepository

class SingleGameRepositoryImpl(private val singleGameNetworkService: SingleGameNetworkService) : SingleGameRepository {
    override suspend fun getGame(id: Int): GamesResponse {
        val response = singleGameNetworkService.getGame(id)
        return response.body() ?: throw IllegalStateException("Game not found for id=$id")
    }
}



