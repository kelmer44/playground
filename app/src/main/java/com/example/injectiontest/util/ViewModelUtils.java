package com.example.injectiontest.util;


import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.lifecycle.AbstractSavedStateViewModelFactory;
import androidx.lifecycle.SavedStateHandle;
import androidx.lifecycle.SavedStateHandleSupport;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelProviders;
import androidx.lifecycle.ViewModelStoreOwner;
import androidx.lifecycle.viewmodel.CreationExtras;

import javax.inject.Provider;

import kotlin.jvm.functions.Function2;

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
    public static <T extends ViewModel> T getViewModel(ViewModelStoreOwner viewModelStoreOwner,
                                                       Class<T> viewModelClass,
                                                       Function2<SavedStateHandle, Bundle, Provider<T>> initializer) {
        return new ViewModelProvider(viewModelStoreOwner, new ViewModelProvider.Factory() {
            @NonNull
            @Override
            public <T extends ViewModel> T create(@NonNull Class<T> modelClass, @NonNull CreationExtras extras) {
                return (T) initializer.invoke(getSavedStateHandle(extras), getDefaultArgs(extras)).get();
            }

            SavedStateHandle getSavedStateHandle(CreationExtras extras) {
                return SavedStateHandleSupport.createSavedStateHandle(extras);
            }

            Bundle getDefaultArgs(CreationExtras extras) {
                return extras.get(SavedStateHandleSupport.DEFAULT_ARGS_KEY);
            }
        }).get(viewModelClass);
    }

    /**
     * Get a {@link ViewModel}of a given type, scoped to the given {@link Fragment}. The difference
     * between this and {@link #getViewModel(ViewModelStoreOwner, Class, Provider)} is that this method will
     * return a {@code ViewModel} that utilizes the {@link SavedStateHandle}.
     *
     * @param activity       Used to create a {@link ViewModelProvider}
     * @param viewModelClass Class of the {@link ViewModel} implementation.
     * @param defaultArgs    A nullable {@link Bundle} that pass in specific default arguments
     *                       to the ViewModel.
     * @param provider       Way to instantiate a new instances if none exists in the fragment's
     *                       ViewModelStore. See {@link SavedStateViewModelProvider}.
     * @param <T>            Type of the {@link ViewModel} implementation.
     * @return A new or existing instance of the given {@link ViewModel} class that can utilize
     *     a {@link SavedStateHandle}.
     */
    public static <T extends ViewModel> T getSavedStateViewModel(
            FragmentActivity activity,
            Class<T> viewModelClass,
            @Nullable Bundle defaultArgs,
            SavedStateViewModelProvider<T> provider
    ) {
        ViewModelProvider.Factory factory
                = new AbstractSavedStateViewModelFactory(activity, defaultArgs) {
            @NonNull
            @Override
            protected <T extends ViewModel> T create(
                    @NonNull String key, @NonNull Class<T> modelClass, @NonNull SavedStateHandle handle
            ) {
                return (T) provider.get(handle);
            }
        };
        return new ViewModelProvider(activity, factory).get(viewModelClass);
    }

    private ViewModelUtils() {
    }

    /**
     * Get a {@link ViewModel}of a given type, scoped to the given {@link Fragment}. The difference
     * between this and {@link #getViewModel(ViewModelStoreOwner, Class, Provider)} is that this method will
     * return a {@code ViewModel} that utilizes the {@link SavedStateHandle}.
     *
     * @param fragment       Used to create a {@link ViewModelProvider}
     * @param viewModelClass Class of the {@link ViewModel} implementation.
     * @param defaultArgs    A nullable {@link Bundle} that pass in specific default arguments
     *                       to the ViewModel.
     * @param provider       Way to instantiate a new instances if none exists in the fragment's
     *                       ViewModelStore. See {@link SavedStateViewModelProvider}.
     * @param <T>            Type of the {@link ViewModel} implementation.
     * @return A new or existing instance of the given {@link ViewModel} class that can utilize
     *     a {@link SavedStateHandle}.
     */
    public static <T extends ViewModel> T getSavedStateViewModel(
            Fragment fragment,
            Class<T> viewModelClass,
            @Nullable Bundle defaultArgs,
            SavedStateViewModelProvider<T> provider
    ) {
        return new ViewModelProvider(fragment, new AbstractSavedStateViewModelFactory(fragment, defaultArgs) {
            @NonNull
            @Override
            protected <T extends ViewModel> T create(
                    @NonNull String key, @NonNull Class<T> modelClass, @NonNull SavedStateHandle handle
            ) {
                return (T) provider.get(handle);
            }
        }).get(viewModelClass);
    }
    /**
     * An interface that is used to offer a {@link SavedStateHandle} for the ViewModel.
     *
     * @param <T> The {@link ViewModel} that is to be returned.
     */
    public interface SavedStateViewModelProvider<T extends ViewModel> {
        /**
         * Returns an instance of a {@link ViewModel}, giving it the passed in
         * {@link SavedStateHandle}.
         *
         * @param handle The {@link SavedStateHandle} used to save state across process and
         *               configuration changes.
         * @return An implementation of {@link ViewModel}.
         */
        T get(SavedStateHandle handle);
    }
}
