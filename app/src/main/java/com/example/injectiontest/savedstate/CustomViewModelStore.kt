package com.example.injectiontest.savedstate

import androidx.fragment.app.Fragment
import androidx.lifecycle.HasDefaultViewModelProviderFactory
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelStore
import androidx.lifecycle.ViewModelStoreOwner
import androidx.lifecycle.viewmodel.CreationExtras
import javax.inject.Inject

class CustomViewModelStore @Inject constructor(
    val fragment: Fragment, private val savedStateViewModelFactory: SavedStateViewModelFactory
) : ViewModelStoreOwner, HasDefaultViewModelProviderFactory {

    override fun getViewModelStore(): ViewModelStore = fragment.viewModelStore

    override fun getDefaultViewModelCreationExtras(): CreationExtras = fragment.defaultViewModelCreationExtras

    override fun getDefaultViewModelProviderFactory(): ViewModelProvider.Factory =
        GenericSavedStateViewModelFactory(savedStateViewModelFactory, fragment)
}
