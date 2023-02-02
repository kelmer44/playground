package com.example.injectiontest.hiltviewmodel

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import timber.log.Timber
import javax.inject.Inject
import javax.inject.Named

@HiltViewModel
class HMVViewModel @Inject constructor(/*@Named("bar") val bar: String*/) : ViewModel() {

    fun hello() {
//        Timber.w("Bar is $bar")
    }
}
