package com.app.project10.di

import com.app.project10.data.repository.auth.AuthRepository
import com.app.project10.data.repository.auth.AuthRepositoryImpl
import com.app.project10.data.repository.userpreferences.UserPreferencesRepository
import com.app.project10.di.NetworkQualifiers.AuthenticatedRetrofit
import com.app.project10.domain.usecase.auth.ValidateTokenUseCase
import com.app.project10.network.services.auth.AuthApiService
import com.app.project10.ui.AuthViewModel
import org.koin.android.ext.koin.androidContext
import org.koin.core.module.dsl.viewModelOf
import org.koin.core.qualifier.named
import org.koin.dsl.module
import retrofit2.Retrofit

val authModule = module {
    viewModelOf(::AuthViewModel)

    factory<AuthRepository> { AuthRepositoryImpl(get()) }
    factory { ValidateTokenUseCase(get(), get()) }
    factory<AuthApiService> {
        get<Retrofit>(named(AuthenticatedRetrofit))
            .create(AuthApiService::class.java)
    }
    single {
        UserPreferencesRepository(
            context = androidContext(),
            appScope = get()
        )
    }
}

