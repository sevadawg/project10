package com.app.project10.di

import com.app.project10.data.repository.games.GamesRepository
import com.app.project10.data.repository.games.GamesRepositoryImpl
import com.app.project10.data.repository.single_game.SingleGameRepository
import com.app.project10.data.repository.single_game.SingleGameRepositoryImpl
import com.app.project10.network.client.OkHttpClientProvider
import com.app.project10.network.interceptors.OfflineInterceptor
import com.app.project10.network.interceptors.OnlineInterceptor
import com.app.project10.network.services.games.GamesNetworkService
import com.app.project10.network.services.single_game.SingleGameNetworkService
import com.app.project10.ui.screens.game.GameViewModel
import com.app.project10.ui.screens.home.MainScreenViewModel
import org.koin.android.ext.koin.androidContext
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.module

val mainModule = module {
    factory { GamesNetworkService(get()) }
    factory<GamesRepository> { GamesRepositoryImpl(get()) }

    factory { SingleGameNetworkService(get()) }
    factory<SingleGameRepository> { SingleGameRepositoryImpl(get()) }

    factory { OfflineInterceptor(androidContext()) }
    factory { OnlineInterceptor() }

    single { OkHttpClientProvider(androidContext(), get(), get()) }

    viewModelOf(::MainScreenViewModel)
    viewModelOf(::GameViewModel)
}