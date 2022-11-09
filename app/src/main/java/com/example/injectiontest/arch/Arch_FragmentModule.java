package com.example.injectiontest.arch;


import androidx.fragment.app.Fragment;

import com.example.injectiontest.util.ViewModelUtils;

import dagger.Module;
import dagger.Provides;
import dagger.hilt.InstallIn;
import dagger.hilt.android.components.FragmentComponent;

@Module
@InstallIn(FragmentComponent.class)
abstract class Arch_FragmentModule {

    @Provides
    static ArchViewModel providesViewModel(Fragment fragment) {
        return ViewModelUtils.getViewModel(
                fragment,
                ArchViewModel.class,
                () -> new ArchViewModel()
        );
    }
}
