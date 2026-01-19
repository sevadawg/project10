package com.app.project10.di

import org.koin.core.qualifier.named

// Defines named qualifiers for Retrofit instances
val LoginRetrofit = named("LoginRetrofit")
val GamesRetrofit = named("GamesRetrofit")
