package com.app.project10.network.interceptors

import com.app.project10.data.repository.userpreferences.UserPreferencesRepository
import okhttp3.Interceptor
import okhttp3.Response

class AuthInterceptor(
    private val userPreferencesRepository: UserPreferencesRepository
) : Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
        val token = userPreferencesRepository.currentToken

        val request = chain.request()

        val newRequest = if (!token.isNullOrEmpty()) {
            request.newBuilder()
                .header("Authorization", "Bearer ${token}")
                .build()
        } else {
            request
        }

        return chain.proceed(newRequest)
    }
}

