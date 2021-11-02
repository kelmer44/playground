package com.example.injectiontest;

import dagger.Binds;
import dagger.Module;
import dagger.Provides;
import dagger.hilt.InstallIn;
import dagger.hilt.android.components.ActivityComponent;
import dagger.hilt.android.components.FragmentComponent;

@Module
@InstallIn(ActivityComponent.class)
public abstract class Main_ActivityModule {

    @Provides
    static String provideString() {
        return "JOLA";
    }
}
