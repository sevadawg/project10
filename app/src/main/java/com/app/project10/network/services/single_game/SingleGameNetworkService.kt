package com.app.project10.network.services.single_game

import com.app.project10.data.dto.Game
import com.app.project10.network.client.OkHttpClientProvider
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class SingleGameNetworkService(private val okHttpClientProvider: OkHttpClientProvider) {

    private val service: SingleGameService

    init {
        val retro = Retrofit.Builder()
            .baseUrl("https://api-nba-v1.p.rapidapi.com/")
            .addConverterFactory(GsonConverterFactory.create())
            .client(okHttpClientProvider.getOkHttpClientBuilder())
            .build()
        service = retro.create(SingleGameService::class.java)
    }

    suspend fun getGame(id: Int): Response<Game> {
        return service.getGame(id)
    }
}