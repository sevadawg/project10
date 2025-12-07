package com.app.project10

import android.app.Application
import com.app.project10.di.mainModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class P10App : Application() {
    override fun onCreate() {
        super.onCreate()
        initKoin()
    }

    private fun initKoin() {
        startKoin {
            androidContext(this@P10App)
            modules(mainModule)
        }
    }
}