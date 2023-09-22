package com.example.injectiontest

import android.app.Application
import coil.ImageLoader
import coil.ImageLoaderFactory
import coil.disk.DiskCache
import coil.request.CachePolicy
import coil.util.DebugLogger
import dagger.hilt.android.HiltAndroidApp
import timber.log.Timber
import javax.inject.Inject

@HiltAndroidApp
class TestApp : Application(), ImageLoaderFactory {

    @Inject
    lateinit var chocho: Chocho

    @Inject
    lateinit var diskCache: DiskCache

    override fun onCreate() {
        super.onCreate()
        Timber.plant(Timber.DebugTree())
    }

    override fun newImageLoader(): ImageLoader {
        return ImageLoader.Builder(this)
            .logger(DebugLogger())
            .memoryCache(null)
            .memoryCachePolicy(CachePolicy.DISABLED)
//            .networkCachePolicy(CachePolicy.DISABLED)
            .diskCache(diskCache)
            .diskCachePolicy(CachePolicy.ENABLED)
            .build()
    }
}
