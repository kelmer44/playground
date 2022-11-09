package com.example.injectiontest.arch

import androidx.lifecycle.ViewModel
import timber.log.Timber

class ArchViewModel : ViewModel() {

    init {
        Timber.i("ArchViewModel created")
    }
}
