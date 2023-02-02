package com.example.injectiontest.component.annotation

import dagger.hilt.DefineComponent
import dagger.hilt.android.components.ActivityComponent

@PlayerRetainedScoped
@DefineComponent(parent = ActivityComponent::class)
interface PlayerRetainedComponent
