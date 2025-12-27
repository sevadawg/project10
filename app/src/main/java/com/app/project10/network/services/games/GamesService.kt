package com.app.project10.network.services.games

import com.app.project10.data.dto.GamesResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Query

interface GamesService {

    @Headers(
        "x-rapidapi-host: api-nba-v1.p.rapidapi.com",
        "x-rapidapi-key: 629096a94cmshe968509d3a9fbb0p188876jsn327d6c0066cc"
    )
    @GET("games")
    suspend fun getGames(@Query("date") date: String): Response<GamesResponse>
}
