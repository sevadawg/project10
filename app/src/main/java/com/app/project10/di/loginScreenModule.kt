package com.app.project10.di

import com.app.project10.data.repository.login.LoginRepository
import com.app.project10.data.repository.login.LoginRepositoryImpl
import com.app.project10.data.repository.user_preferences.UserPreferencesRepository
import com.app.project10.network.services.login.LoginService
import com.app.project10.ui.screens.login.LoginViewModel
import org.koin.android.ext.koin.androidContext
import org.koin.core.module.dsl.viewModelOf
import org.koin.core.qualifier.named
import org.koin.dsl.module
import retrofit2.Retrofit

val loginScreenModule = module {

    factory<LoginService>(named("AuthenticatedRetrofit")) {
        get<Retrofit>(named("AuthenticatedRetrofit"))
            .create(LoginService::class.java)
    }

    factory<LoginRepository> {
        LoginRepositoryImpl(
            loginNetworkService = get(named("AuthenticatedRetrofit"))
        )
    }

    factory<UserPreferencesRepository> {
        UserPreferencesRepository(context = androidContext())
    }

    viewModelOf(::LoginViewModel)
}
