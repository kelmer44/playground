/*
 * Copyright 2023 Sirius XM Holdings Inc. All rights reserved.
 */
package com.example.injectiontest.coiltest

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import com.example.injectiontest.R
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject
import javax.inject.Provider

@AndroidEntryPoint
class CoilTestFragment :Fragment(R.layout.fragment_coil) {
    @Inject
    lateinit var lifecycleObserver: Provider<CoilTestLifecycleObserver>

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewLifecycleOwner.lifecycle.addObserver(lifecycleObserver.get())
    }

    companion object {
        fun newInstance() = CoilTestFragment().also {
            it.arguments = Bundle()
        }
    }
}