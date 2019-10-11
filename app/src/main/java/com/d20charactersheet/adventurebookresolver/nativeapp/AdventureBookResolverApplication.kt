package com.d20charactersheet.adventurebookresolver.nativeapp

import android.app.Application
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin

class AdventureBookResolverApplication : Application() {

    override fun onCreate() {
        super.onCreate()

        startKoin {
            androidLogger()
            androidContext(this@AdventureBookResolverApplication)
            modules(appModule)
        }
    }

}