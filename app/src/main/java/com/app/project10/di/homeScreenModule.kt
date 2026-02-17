package com.app.project10.di

import com.app.project10.data.repository.games.GamesRepository
import com.app.project10.data.repository.games.GamesRepositoryImpl
import com.app.project10.di.NetworkQualifiers.RapidApiRetrofit
import com.app.project10.network.services.games.GamesNetworkService
import com.app.project10.ui.screens.home.HomeViewModel
import org.koin.core.module.dsl.viewModelOf
import org.koin.core.qualifier.named
import org.koin.dsl.module
import retrofit2.Retrofit

val homeScreenModule = module {
    factory<GamesRepository> { GamesRepositoryImpl(get()) }

    viewModelOf(::HomeViewModel)

    factory {
        get<Retrofit>(named(RapidApiRetrofit)).create(GamesNetworkService::class.java)
    }
}

