package com.example.injectiontest.hiltviewmodel;

import javax.inject.Named;

import dagger.Module;
import dagger.Provides;
import dagger.hilt.InstallIn;
import dagger.hilt.android.components.ViewModelComponent;

@Module
@InstallIn(ViewModelComponent.class)
abstract class HVM_Module {

    @Provides
    @Named("bar")
    static String providesBar() {
        return "Bar";
    }
}
