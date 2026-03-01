package com.app.project10.data.remote.api.singlegame

import com.app.project10.data.remote.dto.game.GamesResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface SingleGameNetworkService {
    @GET("games")
    suspend fun getGame(@Query(value = "id") id: Int): Response<GamesResponse>
}


