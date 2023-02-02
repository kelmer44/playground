package com.example.injectiontest.hiltviewmodel

import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class MyModule {


    companion object {
        @JvmStatic
        fun provideMyBinding() :String = "MyBinding"
    }
}
