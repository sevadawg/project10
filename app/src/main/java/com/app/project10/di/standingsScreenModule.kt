package com.app.project10.di

import com.app.project10.data.remote.api.standings.StandingsApiService
import com.app.project10.data.repository.standings.StandingsRepositoryImpl
import com.app.project10.di.NetworkQualifiers.RapidApiRetrofit
import com.app.project10.domain.repository.StandingsRepository
import com.app.project10.presentation.screens.standings.StandingsViewModel
import org.koin.core.module.dsl.viewModelOf
import org.koin.core.qualifier.named
import org.koin.dsl.module
import retrofit2.Retrofit

val standingsScreenModule = module {
    factory<StandingsRepository> { StandingsRepositoryImpl(get()) }

    viewModelOf(::StandingsViewModel)

    factory<StandingsApiService> {
        get<Retrofit>(named(RapidApiRetrofit)).create(StandingsApiService::class.java)
    }
}

