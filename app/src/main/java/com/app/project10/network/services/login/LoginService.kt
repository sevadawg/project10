package com.app.project10.network.services.login

import com.app.project10.data.dto.login.LoginResponse
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.POST

interface LoginService {
    @POST("login")
    @FormUrlEncoded
    suspend fun login(@Field("idToken") idToken: String): LoginResponse
}
