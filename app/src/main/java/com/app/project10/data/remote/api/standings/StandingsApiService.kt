package com.app.project10.data.remote.api.standings

import com.app.project10.data.remote.dto.standings.StandingsResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface StandingsApiService {
    @GET("standings")
    suspend fun getStandings(
        @Query("league") league: String = "standard",
        @Query("season") season: Int
    ): Response<StandingsResponse>
}

