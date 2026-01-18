package com.app.project10.di

/**
 * This is the main Koin module for the application.
 * It aggregates all the feature-specific and shared modules into a single list.
 * This makes the dependency graph modular and easier to manage.
 */
val mainModule = listOf(
    networkModule,
    homeScreenModule,
    loginScreenModule,
    gameScreenModule
)
