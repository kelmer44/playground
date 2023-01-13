package com.example.injectiontest.component

import android.util.Log
import javax.inject.Inject
import javax.inject.Named

class MyStupidClass @Inject constructor(
    @Named("custom_age") private val age: Int,
    @Named("custom_name")
    private val name: String
) {
    init {
        Log.i("Custom", "Age is $age, name is $name")
    }
}
