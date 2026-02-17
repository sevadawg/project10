package com.app.project10.data.remote.interceptor

import com.app.project10.BuildConfig
import okhttp3.Interceptor
import okhttp3.Response

class RapidInterceptor : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val originalRequest = chain.request()
        val newRequest = originalRequest.newBuilder()
            .header("x-rapidapi-host", BuildConfig.RAPID_API_HOST)
            .header("x-rapidapi-key", BuildConfig.RAPID_API_KEY)
            .build()
        return chain.proceed(newRequest)
    }
}

