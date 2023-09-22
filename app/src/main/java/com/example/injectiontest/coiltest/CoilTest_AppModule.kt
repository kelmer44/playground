/*
 * Copyright 2023 Sirius XM Holdings Inc. All rights reserved.
 */
package com.example.injectiontest.coiltest

import android.content.Context
import coil.ImageLoader
import coil.disk.DiskCache
import coil.imageLoader
import coil.request.CachePolicy
import coil.util.DebugLogger
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Response
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
interface CoilTest_AppModule {


    companion object {

        @Provides
        @Singleton
        public fun providesDiskCache(
            @ApplicationContext context: Context
        ): DiskCache =
           DiskCache.Builder()

               .directory(context.filesDir.resolve("image_cache"))
               .maxSizePercent(1.0)
               .build()

        @Provides
        @Singleton
        public fun providesImageLoader(
            @ApplicationContext context: Context,
            diskCache: DiskCache
        ): ImageLoader =
            ImageLoader.Builder(context)
                .okHttpClient(
                    OkHttpClient.Builder().addNetworkInterceptor(
                        Interceptor {
                            chain ->
                            val proceed = chain.proceed(
                                chain.request()
                                    .newBuilder()
                                    .build()
                            )
                            return@Interceptor proceed.newBuilder()
                                .header("cache-control", "public, max-age=5, immutable")
                                .build()
                        }
                    )
                        .build()
                )
                .logger(DebugLogger())
                .crossfade(true)
                .diskCache(diskCache)

                .diskCachePolicy(CachePolicy.ENABLED)
                .build()
    }
}