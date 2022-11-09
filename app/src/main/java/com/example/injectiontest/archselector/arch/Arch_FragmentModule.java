package com.example.injectiontest.archselector.arch;


import androidx.fragment.app.Fragment;

import com.example.injectiontest.archselector.ArchRepository;
import com.example.injectiontest.util.ViewModelUtils;

import dagger.Module;
import dagger.Provides;
import dagger.hilt.InstallIn;
import dagger.hilt.android.components.FragmentComponent;

@Module
@InstallIn(FragmentComponent.class)
abstract class Arch_FragmentModule {

    @Provides
    static ArchViewModel providesViewModel(Fragment fragment,
                                           ArchRepository repository) {
        return ViewModelUtils.getViewModel(
                fragment,
                ArchViewModel.class,
                () -> new ArchViewModel(repository)
        );
    }
}
