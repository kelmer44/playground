/*
 * Copyright 2023 Sirius XM Holdings Inc. All rights reserved.
 */
package com.example.injectiontest.coiltest

import androidx.lifecycle.DefaultLifecycleObserver
import javax.inject.Inject

class CoilTestLifecycleObserver @Inject constructor(
    private val presenter: CoilTestPresenter
) : DefaultLifecycleObserver {
}