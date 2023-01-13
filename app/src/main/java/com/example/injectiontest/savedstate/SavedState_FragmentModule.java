package com.example.injectiontest.savedstate;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.SavedStateHandle;

import com.example.injectiontest.util.ViewModelUtils;

import javax.inject.Named;

import dagger.Binds;
import dagger.Module;
import dagger.Provides;
import dagger.hilt.InstallIn;
import dagger.hilt.android.components.FragmentComponent;

@Module
@InstallIn(FragmentComponent.class)
abstract class SavedState_FragmentModule {


    @Provides
    @Named("foo")
    static String providesFoo() {
        return "Foo";
    }

    //
//    @Provides
//    static SavedStateViewModel provideViewModel(Fragment fragment, @Named("foo") String foo) {
//
//        return ViewModelUtils.getViewModel(fragment, SavedStateViewModel.class, (handle, bundle) -> () -> new SavedStateViewModel(handle, foo));
//
////        return ViewModelUtils.getSavedStateViewModel(fragment, SavedStateViewModel.class, fragment.getArguments(), new ViewModelUtils.SavedStateViewModelProvider<SavedStateViewModel>() {
////            @Override
////            public SavedStateViewModel get(SavedStateHandle handle) {
////                return new SavedStateViewModel();
////            }
////        });
//    }
}
