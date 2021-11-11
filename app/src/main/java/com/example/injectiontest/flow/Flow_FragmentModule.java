package com.example.injectiontest.flow;

import androidx.fragment.app.Fragment;

import com.example.injectiontest.util.ViewModelUtils;

import dagger.Module;
import dagger.Provides;
import dagger.hilt.InstallIn;
import dagger.hilt.android.components.FragmentComponent;

@Module
@InstallIn(FragmentComponent.class)
abstract class Flow_FragmentModule {

    @Provides
    static FlowViewModel provideFlowViewModel(
            Fragment fragment
    ) {
        return ViewModelUtils.getViewModel(fragment,
                FlowViewModel.class,
                () -> new FlowViewModel()
        );
    }
}
