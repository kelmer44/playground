package com.example.injectiontest.component.annotation

import dagger.hilt.DefineComponent
import dagger.hilt.android.components.ActivityComponent
import dagger.hilt.android.components.ActivityRetainedComponent

@PlayerRetainedScoped
//@DefineComponent(parent = ActivityRetainedComponent::class)
@DefineComponent(parent = ActivityComponent::class)
interface PlayerRetainedComponent
