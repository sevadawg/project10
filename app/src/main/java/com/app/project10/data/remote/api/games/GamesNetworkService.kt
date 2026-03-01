package com.app.project10.data.remote.api.games

import com.app.project10.data.remote.dto.game.GamesResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface GamesNetworkService {
    @GET("games")
    suspend fun getGames(@Query("date") date: String): Response<GamesResponse>
}


