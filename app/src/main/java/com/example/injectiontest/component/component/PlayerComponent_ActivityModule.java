package com.example.injectiontest.component.component;

import dagger.Binds;
import dagger.Module;
import dagger.hilt.InstallIn;
import dagger.hilt.android.components.ActivityComponent;

@Module
@InstallIn(ActivityComponent.class)
abstract class PlayerComponent_ActivityModule {

    @Binds
    abstract PlayerComponentHolder.Factory factory(PlayerComponentHolderImpl.Factory factory);
}
