package com.android.astanatimes

import android.app.Application
import android.util.Log
import com.android.astanatimes.KoinModules
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class App: Application() {
    override fun onCreate() {
        super.onCreate()

        startKoin {
            androidContext(this@App)
            modules(KoinModules.modules)
        }
    }
}