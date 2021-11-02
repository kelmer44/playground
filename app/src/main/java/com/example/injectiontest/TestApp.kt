package com.example.injectiontest

import android.app.Application
import dagger.hilt.android.HiltAndroidApp
import timber.log.Timber
import javax.inject.Inject

@HiltAndroidApp
class TestApp : Application() {

    @Inject
    lateinit var chocho: Chocho

    override fun onCreate() {

        super.onCreate()
        Timber.plant(Timber.DebugTree())
        Timber.w("CHOCHO IS $chocho")

    }
}
