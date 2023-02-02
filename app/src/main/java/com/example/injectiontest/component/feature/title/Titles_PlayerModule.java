package com.example.injectiontest.component.feature.title;

import com.example.injectiontest.component.annotation.PlayerViewComponent;
import com.example.injectiontest.component.annotation.PlayerViewScoped;
import com.example.injectiontest.component.feature.PlayerFeature;
import com.example.injectiontest.component.feature.PlayerFeatureKey;
import com.example.injectiontest.component.feature.PlayerFeatureMapKey;
import com.example.injectiontest.component.feature.SimplePlayerFeature;
import com.example.injectiontest.component.view.PlayerView;

import javax.inject.Provider;

import dagger.Binds;
import dagger.Module;
import dagger.Provides;
import dagger.hilt.InstallIn;
import dagger.multibindings.IntoMap;

@Module
@InstallIn(PlayerViewComponent.class)
abstract class Titles_PlayerModule {

    @Binds
    @IntoMap
    @PlayerFeatureMapKey(PlayerFeatureKey.TITLE)
    abstract PlayerFeature playerFeature(SimplePlayerFeature<TitlesLifecycleObserver> playerFeature);

    @Provides
    @PlayerViewScoped
    static TitlesViews providesTitlesViews(Provider<PlayerView> playerControlsProvider) {
        TitlesViews containerControl = (TitlesViews) playerControlsProvider.get();
        return containerControl;
    }
}
