package com.app.project10.data.repository.login

import com.app.project10.data.dto.login.GoogleLoginRequest
import com.app.project10.data.dto.login.LoginResponse
import com.app.project10.network.services.login.LoginService

class LoginRepositoryImpl(
    private val loginNetworkService: LoginService
) : LoginRepository {
    override suspend fun login(idToken: String): LoginResponse {
        return loginNetworkService.login(GoogleLoginRequest(idToken = idToken))
    }
}