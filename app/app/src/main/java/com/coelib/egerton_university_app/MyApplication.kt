/*
package com.coelib.egerton_university_app


import android.app.Application
import com.coelib.egerton_university_app.di.appModule
import org.koin.android.ext.koin.androidContext

import org.koin.core.context.startKoin
import org.koin.core.logger.Level
import org.koin.core.logger.PrintLogger

class MyApp : Application() {
    override fun onCreate() {
        super.onCreate()

        // Start Koin with detailed logging
        startKoin {
            androidContext(this@MyApp)
            logger(PrintLogger(Level.DEBUG))  // This will print all Koin activity in Logcat
            modules(appModule)
        }
    }
}


 */