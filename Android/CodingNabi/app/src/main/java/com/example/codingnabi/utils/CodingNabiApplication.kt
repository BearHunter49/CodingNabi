package com.example.codingnabi.utils

import android.app.Application
import timber.log.Timber

class CodingNabiApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        Timber.plant(Timber.DebugTree())
    }

    companion object{
        var databaseFileVersion = "0"
    }
}