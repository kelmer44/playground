package com.example.injectiontest.savedstate;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.SavedStateHandle;

import com.example.injectiontest.util.ViewModelUtils;

import dagger.Module;
import dagger.Provides;
import dagger.hilt.InstallIn;
import dagger.hilt.android.components.FragmentComponent;

@Module
@InstallIn(FragmentComponent.class)
class SavedState_FragmentModule {
//
//    @Provides
//    static SavedStateViewModel provideViewModel(Fragment fragment) {
//
//        return ViewModelUtils.getViewModel(fragment, SavedStateViewModel.class,
//        (handle,bundle) -> () -> new SavedStateViewModel());

//        return ViewModelUtils.getSavedStateViewModel(fragment, SavedStateViewModel.class, fragment.getArguments(), new ViewModelUtils.SavedStateViewModelProvider<SavedStateViewModel>() {
//            @Override
//            public SavedStateViewModel get(SavedStateHandle handle) {
//                return new SavedStateViewModel();
//            }
//        });
//    }
}
