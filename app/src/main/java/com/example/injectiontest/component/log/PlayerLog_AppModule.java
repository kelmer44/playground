package com.example.injectiontest.component.log;

import dagger.Module;
import dagger.Provides;
import dagger.hilt.InstallIn;
import dagger.hilt.components.SingletonComponent;

@Module
@InstallIn(SingletonComponent.class)
abstract class PlayerLog_AppModule {

    @Provides
    static PlayerLog playerLog() {
        return PlayerLogImpl.INSTANCE;
    }
}
