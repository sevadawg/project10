package com.app.project10.network.services

import com.app.project10.data.models.GamesModel
import com.app.project10.network.client.OkHttpClientProvider
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class GamesNetworkService (private val okHttpClientProvider: OkHttpClientProvider) {

    companion object {
        private const val GAMES_URL = "https://api-basketball.p.rapidapi.com/"
        private const val NBA_LEAGUE_ID = "12"
    }

    suspend fun getTodayGames(todayDate: String): Response<GamesModel> {
        val retro = Retrofit.Builder()
            .baseUrl(GAMES_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .client(okHttpClientProvider.getOkHttpClientBuilder())
            .build()

        val service = retro.create(GamesService::class.java)
        return service.listOfTodayGames(NBA_LEAGUE_ID, todayDate)
    }
}

