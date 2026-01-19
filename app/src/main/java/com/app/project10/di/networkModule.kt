package com.app.project10.di

import com.app.project10.data.repository.user_preferences.UserPreferencesRepository
import com.app.project10.network.client.OkHttpClientProvider
import com.app.project10.network.interceptors.AuthInterceptor
import com.app.project10.network.interceptors.OfflineInterceptor
import com.app.project10.network.interceptors.OnlineInterceptor
import okhttp3.OkHttpClient
import org.koin.android.ext.koin.androidContext
import org.koin.core.qualifier.named
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

val networkModule = module {
    // Provide UserPreferencesRepository
    single { UserPreferencesRepository(androidContext()) }

    // Provide Interceptors
    factory { OfflineInterceptor(androidContext()) }
    factory { OnlineInterceptor() }
    factory { AuthInterceptor(get()) } // AuthInterceptor now gets UserPreferencesRepository

    // Provide OkHttpClient
    single<OkHttpClient> {
        OkHttpClientProvider(
            context = androidContext(),
            offlineInterceptor = get(),
            onlineInterceptor = get(),
            authInterceptor = get()
        ).getOkHttpClientBuilder()
    }

    // Retrofit for Authentication
    single<Retrofit>(named("LoginRetrofit")) {
        Retrofit.Builder()
            .baseUrl("https://your.auth.api.url/") // <-- TODO: REPLACE WITH AUTH URL
            .client(get<OkHttpClient>())
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    // Retrofit for Games API
    single<Retrofit>(named("GamesRetrofit")) {
        Retrofit.Builder()
            .baseUrl("https://api-nba-v1.p.rapidapi.com/")
            .client(get<OkHttpClient>())
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }
}
