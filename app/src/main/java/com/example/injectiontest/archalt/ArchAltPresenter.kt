package com.example.injectiontest.archalt

import timber.log.Timber
import javax.inject.Inject

class ArchAltPresenter @Inject constructor() {

    init {
        Timber.v("ArchAltPresenter created")
    }
}
