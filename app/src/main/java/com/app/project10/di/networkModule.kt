package com.app.project10.di

import com.app.project10.BuildConfig
import com.app.project10.network.interceptors.AuthInterceptor
import com.app.project10.network.interceptors.RapidInterceptor
import okhttp3.OkHttpClient
import org.koin.core.qualifier.named
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.converter.scalars.ScalarsConverterFactory

val networkModule = module {

    // --- Client for RapidAPI Services (Games, SingleGame) ---
    single<OkHttpClient>(named("RapidApiClient")) {
        OkHttpClient.Builder()
            .addInterceptor(RapidInterceptor())
            // You can add other specific interceptors like logging here
            .build()
    }

    single<Retrofit>(named("RapidApiRetrofit")) {
        Retrofit.Builder()
            .baseUrl(BuildConfig.BASE_URL)
            .client(get(named("RapidApiClient")))
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    // --- Default Client for Authenticated Services ---
    single<OkHttpClient>(named("AuthenticatedClient")) {
        OkHttpClient.Builder()
            .addInterceptor(AuthInterceptor(get()))
            .build()
    }

    single<Retrofit>(named("AuthenticatedRetrofit")) {
        Retrofit.Builder()
            .baseUrl("http://127.0.0.1:8080")
            .client(get(named("AuthenticatedClient")))
            .addConverterFactory(ScalarsConverterFactory.create())
            .build()
    }

    // --- Default Retrofit Instance ---
    // This Retrofit instance will be used by any service that doesn't request a named one.
    // It will automatically use the default OkHttpClient with the AuthInterceptor.
    single<Retrofit> {
        Retrofit.Builder()
            .client(get()) // Gets the default OkHttpClient
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }
}
