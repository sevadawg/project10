package com.app.project10.di

import com.app.project10.data.repository.login.LoginRepository
import com.app.project10.data.repository.login.LoginRepositoryImpl
import com.app.project10.network.services.login.LoginService
import com.app.project10.ui.screens.login.LoginViewModel
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.module
import retrofit2.Retrofit

val loginScreenModule = module {
    factory<LoginRepository> { LoginRepositoryImpl(get()) }

    viewModelOf(::LoginViewModel)

    // Provide LoginService using the 'LoginRetrofit' named qualifier
    factory {
        get<Retrofit>(LoginRetrofit).create(LoginService::class.java)
    }
}
