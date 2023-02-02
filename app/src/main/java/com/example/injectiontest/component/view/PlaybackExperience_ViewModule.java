package com.example.injectiontest.component.view;

import dagger.Binds;
import dagger.Module;
import dagger.hilt.InstallIn;
import dagger.hilt.android.components.ViewComponent;

@Module
@InstallIn(ViewComponent.class)
abstract class PlaybackExperience_ViewModule {
    @Binds
    abstract PlaybackExperienceView.Presenter bindPlaybackExperiencePresenter(PlaybackExperiencePresenter presenter);
}
