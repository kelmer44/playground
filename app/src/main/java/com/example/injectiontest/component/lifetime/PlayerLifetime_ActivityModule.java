package com.example.injectiontest.component.lifetime;

import com.example.injectiontest.component.annotation.PlayerRetainedComponent;

import dagger.Binds;
import dagger.Module;
import dagger.hilt.InstallIn;
import dagger.hilt.android.components.ActivityComponent;

@Module
//@InstallIn(ActivityComponent.class)
@InstallIn(PlayerRetainedComponent.class)
abstract class PlayerLifetime_ActivityModule {

    @Binds
    abstract PlayerLifetime playerLifetime(PlayerLifetimeImpl playerLifetime);
}
