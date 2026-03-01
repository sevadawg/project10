package com.app.project10.data.repository.login

import com.app.project10.data.remote.api.login.LoginService
import com.app.project10.data.remote.dto.login.GoogleLoginRequest
import com.app.project10.data.remote.dto.login.LoginResponse
import com.app.project10.domain.repository.LoginRepository

class LoginRepositoryImpl(
    private val loginNetworkService: LoginService
) : LoginRepository {
    override suspend fun login(idToken: String): LoginResponse {
        return loginNetworkService.login(GoogleLoginRequest(idToken = idToken))
    }
}

