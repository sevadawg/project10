package com.app.project10.di

import com.app.project10.data.repository.single_game.SingleGameRepository
import com.app.project10.data.repository.single_game.SingleGameRepositoryImpl
import com.app.project10.network.services.single_game.SingleGameNetworkService
import com.app.project10.ui.screens.game.GameViewModel
import org.koin.core.module.dsl.viewModelOf
import org.koin.core.qualifier.named
import org.koin.dsl.module
import retrofit2.Retrofit

val gameScreenModule = module {
    factory<SingleGameRepository> { SingleGameRepositoryImpl(get()) }

    viewModelOf(::GameViewModel)

    factory<SingleGameNetworkService> {
        get<Retrofit>(named("RapidApiRetrofit")).create(SingleGameNetworkService::class.java)
    }
}
