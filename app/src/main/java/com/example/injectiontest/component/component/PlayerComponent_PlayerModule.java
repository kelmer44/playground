package com.example.injectiontest.component.component;

import com.example.injectiontest.component.annotation.PlayerViewComponent;
import com.example.injectiontest.component.feature.PlayerFeature;
import com.example.injectiontest.component.feature.PlayerFeatureKey;

import java.util.Map;

import dagger.Module;
import dagger.hilt.InstallIn;
import dagger.multibindings.Multibinds;

@Module
@InstallIn(PlayerViewComponent.class)
abstract class PlayerComponent_PlayerModule {

    @Multibinds
    abstract Map<PlayerFeatureKey, PlayerFeature> bindsPlayerFeatureMap();

}
