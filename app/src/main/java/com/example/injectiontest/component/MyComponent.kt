package com.example.injectiontest.component

import dagger.hilt.DefineComponent
import dagger.hilt.android.components.FragmentComponent

@DefineComponent(parent = FragmentComponent::class)
interface MyComponent
