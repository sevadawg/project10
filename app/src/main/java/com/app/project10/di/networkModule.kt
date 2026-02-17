package com.app.project10.di

import com.app.project10.BuildConfig
import com.app.project10.di.NetworkQualifiers.AuthenticatedClient
import com.app.project10.di.NetworkQualifiers.AuthenticatedRetrofit
import com.app.project10.di.NetworkQualifiers.RapidApiClient
import com.app.project10.di.NetworkQualifiers.RapidApiRetrofit
import com.app.project10.network.interceptors.AuthInterceptor
import com.app.project10.network.interceptors.RapidInterceptor
import okhttp3.OkHttpClient
import org.koin.core.qualifier.named
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

val networkModule = module {
    single<OkHttpClient>(named(RapidApiClient)) {
        OkHttpClient.Builder()
            .addInterceptor(RapidInterceptor())
            .build()
    }

    single<Retrofit>(named(RapidApiRetrofit)) {
        Retrofit.Builder()
            .baseUrl(BuildConfig.BASE_URL)
            .client(get(named(RapidApiClient)))
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    single<OkHttpClient>(named(AuthenticatedClient)) {
        OkHttpClient.Builder()
            .addInterceptor(AuthInterceptor(get()))
            .build()
    }

    single<Retrofit>(named(AuthenticatedRetrofit)) {
        Retrofit.Builder()
            .baseUrl("http://10.0.2.2:8080/") //  local Ktor
            .client(get(named(AuthenticatedClient)))
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }
}

