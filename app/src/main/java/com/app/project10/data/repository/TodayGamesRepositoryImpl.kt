package com.app.project10.data.repository

import android.util.Log
import com.app.project10.data.models.GamesResponse
import com.app.project10.network.services.GamesNetworkService

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class TodayGamesRepositoryImpl(private val gamesNetworkService: GamesNetworkService) :
    TodayGamesRepository {
    override fun getTodayGames(todayDate: String): Flow<List<GamesResponse>> = flow {
        try {
            val response = gamesNetworkService.getTodayGames(todayDate)

            if (response.isSuccessful && response.body()?.errors.isNullOrEmpty() && response.body()?.response != null) {
                emit(response.body()!!.response)
            } else {
                emit(emptyList())
            }
        } catch (error: Exception) {
            emit(emptyList())
        }
    }
}