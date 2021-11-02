package com.example.injectiontest.util;


import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelProviders;

import javax.inject.Provider;

/**
 * Utility methods for getting a {@link ViewModel} from a {@link FragmentActivity} or a
 * {@link Fragment}, reducing the amount of boilerplate required in dagger modules.
 */
@SuppressWarnings({"unchecked", "TypeParameterHidesVisibleType"})
public final class ViewModelUtils {

    /**
     * Get a {@link ViewModel} of a given type, scoped to the given {@link Fragment}.
     *
     * @param fragment       Used to create a {@link ViewModelProvider}
     * @param viewModelClass Class of the {@link ViewModel} implementation.
     * @param provider       Way to instantiate a new instances if none exists in the fragment's
     *                       ViewModelStore
     * @param <T>            Type of the {@link ViewModel} implementation.
     * @return A new or existing instance of the given {@link ViewModel} class.
     */
    public static <T extends ViewModel> T getViewModel(Fragment fragment,
                                                       Class<T> viewModelClass,
                                                       Provider<T> provider) {
        return ViewModelProviders.of(fragment, new ViewModelProvider.Factory() {
            @NonNull
            @Override
            public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
                return (T) provider.get();
            }
        }).get(viewModelClass);
    }

//    /**
//     * Get a {@link ViewModel} of a given type, scoped to the given {@link FragmentActivity}.
//     *
//     * @param activity       Used to create a {@link ViewModelProvider}
//     * @param viewModelClass Class of the {@link ViewModel} implementation.
//     * @param provider       Way to instantiate a new instances if none exists in the fragment's
//     *                       ViewModelStore
//     * @param <T>            Type of the {@link ViewModel} implementation.
//     * @return A new or existing instance of the given {@link ViewModel} class.
//     */
//    public static <T extends ViewModel> T getViewModel(FragmentActivity activity,
//                                                       Class<T> viewModelClass,
//                                                       Provider<T> provider) {
//        return ViewModelProviders.of(activity, new ViewModelProvider.Factory() {
//            @NonNull
//            @Override
//            public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
//                return (T) provider.get();
//            }
//        }).get(viewModelClass);
//    }


    private ViewModelUtils() {
    }

}
