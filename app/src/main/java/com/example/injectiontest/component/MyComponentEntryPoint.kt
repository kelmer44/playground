package com.example.injectiontest.component

import dagger.hilt.EntryPoint
import dagger.hilt.InstallIn

@EntryPoint
@InstallIn(MyComponent::class)
interface MyComponentEntryPoint {
    fun getName(): String

    fun getMyClass() : MyStupidClass
}
