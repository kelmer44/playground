package com.example.injectiontest;

import android.app.Activity;

import androidx.fragment.app.FragmentActivity;
import androidx.lifecycle.SavedStateHandle;
import androidx.lifecycle.ViewModelProvider;

import com.example.injectiontest.util.ViewModelUtils;

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

    @Provides
    static MainActivityViewModel provideMainActivityViewModel(FragmentActivity activity) {

        return ViewModelUtils.getSavedStateViewModel(
                activity,
                MainActivityViewModel.class,
                activity.getIntent().getExtras(),
                handle -> new MainActivityViewModel());
    }
}
