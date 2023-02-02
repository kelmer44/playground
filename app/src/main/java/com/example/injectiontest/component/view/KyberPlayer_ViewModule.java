package com.example.injectiontest.component.view;

import com.example.injectiontest.component.annotation.PlaybackExperienceMapKey;
import com.example.injectiontest.component.annotation.PlayerViewComponent;
import com.example.injectiontest.component.experience.PlaybackExperienceKey;

import dagger.Binds;
import dagger.Module;
import dagger.hilt.InstallIn;
import dagger.multibindings.IntoMap;

/**
 * Module for providing KyberPlayerView components.
 */
@Module
@InstallIn(PlayerViewComponent.class)
public abstract class KyberPlayer_ViewModule {

    @Binds
    @IntoMap
    @PlaybackExperienceMapKey(PlaybackExperienceKey.Kyber)
    abstract PlayerView bindsKyberPlayerViewMap(KyberPlayerView playerView);
}
