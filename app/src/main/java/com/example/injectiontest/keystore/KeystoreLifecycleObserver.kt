package com.example.injectiontest.keystore

import androidx.lifecycle.DefaultLifecycleObserver
import javax.inject.Inject

class KeystoreLifecycleObserver @Inject constructor(
    private val presenter: KeystorePresenter
) : DefaultLifecycleObserver {
}