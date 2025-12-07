package com.app.project10.di

import com.app.project10.data.repository.TodayGamesRepository
import com.app.project10.data.repository.TodayGamesRepositoryImpl
import com.app.project10.network.client.OkHttpClientProvider
import com.app.project10.network.interceptors.OfflineInterceptor
import com.app.project10.network.interceptors.OnlineInterceptor
import com.app.project10.network.services.GamesNetworkService
import com.app.project10.ui.screens.home.MainScreenViewModel
import org.koin.android.ext.koin.androidContext
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.module

val mainModule = module {
    factory { GamesNetworkService(get()) }
    factory<TodayGamesRepository> { TodayGamesRepositoryImpl(get()) }
    factory { OfflineInterceptor(androidContext()) }
    factory { OnlineInterceptor() }

    single { OkHttpClientProvider(androidContext(), get(), get()) }

    viewModelOf(::MainScreenViewModel)
}