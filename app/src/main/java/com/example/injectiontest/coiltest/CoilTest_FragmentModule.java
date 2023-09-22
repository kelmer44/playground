package com.example.injectiontest.coiltest;

import androidx.fragment.app.Fragment;

import com.example.injectiontest.flow.FlowViewModel;
import com.example.injectiontest.util.ViewModelUtils;

import dagger.Module;
import dagger.Provides;
import dagger.hilt.InstallIn;
import dagger.hilt.android.components.FragmentComponent;

@Module
@InstallIn(FragmentComponent.class)
public class CoilTest_FragmentModule {
    @Provides
    static CoilTestViewModel provideCoilViewModel(
            Fragment fragment
    ) {
        return ViewModelUtils.getViewModel(fragment,
                CoilTestViewModel.class,
                () -> new CoilTestViewModel()
        );
    }
}
