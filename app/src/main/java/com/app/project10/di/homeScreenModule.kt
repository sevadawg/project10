package com.app.project10.di

import com.app.project10.data.repository.games.GamesRepository
import com.app.project10.data.repository.games.GamesRepositoryImpl
import com.app.project10.network.services.games.GamesNetworkService
import com.app.project10.ui.screens.home.MainScreenViewModel
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.module
import retrofit2.Retrofit

val homeScreenModule = module {
    factory<GamesRepository> { GamesRepositoryImpl(get()) }

    viewModelOf(::MainScreenViewModel)

    // Provide GamesNetworkService using the 'GamesRetrofit' named qualifier
    factory {
        get<Retrofit>(GamesRetrofit).create(GamesNetworkService::class.java)
    }
}
