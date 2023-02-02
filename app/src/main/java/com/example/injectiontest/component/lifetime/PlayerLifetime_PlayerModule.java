package com.example.injectiontest.component.lifetime;

import com.example.injectiontest.component.annotation.PlayerViewComponent;
import com.example.injectiontest.component.feature.PlayerFeature;
import com.example.injectiontest.component.feature.PlayerFeatureKey;
import com.example.injectiontest.component.feature.PlayerFeatureMapKey;

import dagger.Binds;
import dagger.Module;
import dagger.hilt.InstallIn;
import dagger.multibindings.IntoMap;

@Module
@InstallIn(PlayerViewComponent.class)
abstract class PlayerLifetime_PlayerModule {

    @Binds
    @IntoMap
    @PlayerFeatureMapKey(PlayerFeatureKey.CORE_LIFETIME)
    abstract PlayerFeature playerFeature(PlayerLifetimePlayerFeature playerFeature);
}
