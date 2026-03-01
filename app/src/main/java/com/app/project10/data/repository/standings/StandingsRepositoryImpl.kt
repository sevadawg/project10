package com.app.project10.data.repository.standings

import com.app.project10.data.remote.api.standings.StandingsApiService
import com.app.project10.data.remote.dto.standings.Standing
import com.app.project10.domain.repository.StandingsRepository

class StandingsRepositoryImpl(
    private val standingsApiService: StandingsApiService
) : StandingsRepository {
    override suspend fun getStandings(season: Int): List<Standing> {
        val response = standingsApiService.getStandings(season = season)
        if (!response.isSuccessful) {
            throw IllegalStateException("Failed to load standings for season=$season: ${response.code()} ${response.message()}")
        }
        val body = response.body()
            ?: throw IllegalStateException("Empty standings response body for season=$season")
        return body.response
    }
}

