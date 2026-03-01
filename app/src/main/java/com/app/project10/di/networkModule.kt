package com.app.project10.di

import com.app.project10.BuildConfig
import com.app.project10.data.remote.interceptor.AuthInterceptor
import com.app.project10.data.remote.interceptor.OfflineCacheInterceptor
import com.app.project10.data.remote.interceptor.RapidInterceptor
import com.app.project10.di.NetworkQualifiers.AuthenticatedClient
import com.app.project10.di.NetworkQualifiers.AuthenticatedRetrofit
import com.app.project10.di.NetworkQualifiers.RapidApiClient
import com.app.project10.di.NetworkQualifiers.RapidApiRetrofit
import okhttp3.Cache
import okhttp3.OkHttpClient
import org.koin.android.ext.koin.androidContext
import org.koin.core.qualifier.named
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.io.File

val networkModule = module {
    single<OkHttpClient>(named(RapidApiClient)) {
        val cacheDirectory = File(androidContext().cacheDir, "rapid_api_cache")
        val cacheSize = 20L * 1024L * 1024L // 20 MB

        OkHttpClient.Builder()
            .cache(Cache(cacheDirectory, cacheSize))
            .addInterceptor(OfflineCacheInterceptor(androidContext()))
            .addInterceptor(RapidInterceptor())
            .addNetworkInterceptor { chain ->
                val response = chain.proceed(chain.request())
                response.newBuilder()
                    .removeHeader("Pragma")
                    .header("Cache-Control", "public, max-age=86400")
                    .build()
            }
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
