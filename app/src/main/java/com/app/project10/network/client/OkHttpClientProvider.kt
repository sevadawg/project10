package com.app.project10.network.client

import android.content.Context
import com.app.project10.network.interceptors.OfflineInterceptor
import com.app.project10.network.interceptors.OnlineInterceptor
import okhttp3.Cache
import okhttp3.OkHttpClient

class OkHttpClientProvider (
    private val context: Context,
    private val offlineInterceptor: OfflineInterceptor,
    private val onlineInterceptor: OnlineInterceptor
) {
    fun getOkHttpClientBuilder(): OkHttpClient {

        val cacheSize = (10 * 1024 * 1024).toLong() // 10 MB
        val cache = Cache(context.cacheDir, cacheSize)

        return OkHttpClient
            .Builder()
            .addInterceptor(offlineInterceptor)
            .addNetworkInterceptor(onlineInterceptor)
            .cache(cache)
            .build()
    }
}