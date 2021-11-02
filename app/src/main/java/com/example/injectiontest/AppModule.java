package com.example.injectiontest;

import dagger.Module;
import dagger.Provides;
import dagger.hilt.InstallIn;
import dagger.hilt.components.SingletonComponent;

@Module
@InstallIn(SingletonComponent.class)
abstract class AppModule {

    @Provides
    static Chocho providesChocho() {
        return new Chocho("Iria", 34);
    }
}
