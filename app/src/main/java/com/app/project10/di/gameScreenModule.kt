package com.app.project10.di

import com.app.project10.data.repository.single_game.SingleGameRepository
import com.app.project10.data.repository.single_game.SingleGameRepositoryImpl
import com.app.project10.network.services.single_game.SingleGameNetworkService
import com.app.project10.ui.screens.game.GameViewModel
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.module
import retrofit2.Retrofit

val gameScreenModule = module {
    factory<SingleGameRepository> { SingleGameRepositoryImpl(get()) }

    viewModelOf(::GameViewModel)

    // Provide SingleGameNetworkService using the 'GamesRetrofit' named qualifier
    factory<SingleGameNetworkService> {
        get<Retrofit>(GamesRetrofit).create(SingleGameNetworkService::class.java)
    }
}
