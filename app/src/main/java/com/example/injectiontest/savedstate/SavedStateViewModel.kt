package com.example.injectiontest.savedstate

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import timber.log.Timber
import javax.inject.Inject
import javax.inject.Named

class SavedStateViewModel(
    private val handle: SavedStateHandle, private val foo: String
) : ViewModel() {
    fun hola() {
        Timber.w("Hola")
    }
}


class SavedStateViewModelFactory @Inject constructor(
    @Named("foo") private val foo: String
) : ViewModelAssistedFactory<SavedStateViewModel> {
    override fun create(handle: SavedStateHandle): SavedStateViewModel {
        return SavedStateViewModel(handle, foo)
    }
}
