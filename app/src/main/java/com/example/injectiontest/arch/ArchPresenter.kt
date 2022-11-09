package com.example.injectiontest.arch

import timber.log.Timber
import javax.inject.Inject

class ArchPresenter @Inject constructor(
    private val viewModel: ArchViewModel
) {

    init {
        Timber.v("ArchPresenter created")
    }
}
