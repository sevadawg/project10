package com.app.project10.network.services

import com.app.project10.data.dto.GamesResponse
import com.app.project10.network.client.OkHttpClientProvider
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class GamesNetworkService (private val okHttpClientProvider: OkHttpClientProvider) {

    private val service: GamesService

    init {
        val retro = Retrofit.Builder()
            .baseUrl("https://api-nba-v1.p.rapidapi.com/")
            .addConverterFactory(GsonConverterFactory.create())
            .client(okHttpClientProvider.getOkHttpClientBuilder())
            .build()
        service = retro.create(GamesService::class.java)
    }

    suspend fun getTodayGames(todayDate: String): Response<GamesResponse> {
        return service.getTodayGames(todayDate)
    }
}
