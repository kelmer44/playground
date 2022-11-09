package com.example.injectiontest.archalt;


import androidx.fragment.app.Fragment;

import com.example.injectiontest.arch.ArchViewModel;
import com.example.injectiontest.util.ViewModelUtils;

import dagger.Module;
import dagger.Provides;
import dagger.hilt.InstallIn;
import dagger.hilt.android.components.FragmentComponent;

@Module
@InstallIn(FragmentComponent.class)
abstract class ArchAlt_FragmentModule {

    @Provides
    static ArchAltViewModel providesViewModel(Fragment fragment) {
        return ViewModelUtils.getViewModel(
                fragment,
                ArchAltViewModel.class,
                () -> new ArchAltViewModel()
        );
    }
}
