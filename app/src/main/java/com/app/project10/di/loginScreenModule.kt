package com.app.project10.di

import com.app.project10.data.repository.login.LoginRepository
import com.app.project10.data.repository.login.LoginRepositoryImpl
import com.app.project10.data.repository.user_preferences.UserPreferencesRepository
import com.app.project10.network.services.login.LoginService
import com.app.project10.ui.screens.login.LoginViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
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

    single {
        CoroutineScope(SupervisorJob() + Dispatchers.Default)
    }

    factory<UserPreferencesRepository> {
        UserPreferencesRepository(scope = get(), context = androidContext())
    }

    viewModelOf(::LoginViewModel)
}
