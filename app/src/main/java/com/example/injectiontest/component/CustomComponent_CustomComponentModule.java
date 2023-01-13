package com.example.injectiontest.component;

import javax.inject.Named;

import dagger.Module;
import dagger.Provides;
import dagger.hilt.InstallIn;

@Module
@InstallIn(MyComponent.class)
class CustomComponent_CustomComponentModule {

    @Provides
    @Named("custom_age")
    static Integer providesAge() {
        return 36;
    }
}
