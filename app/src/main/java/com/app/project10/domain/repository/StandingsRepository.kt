package com.app.project10.domain.repository

import com.app.project10.data.remote.dto.standings.Standing

interface StandingsRepository {
    suspend fun getStandings(season: Int): List<Standing>
}

