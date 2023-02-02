package com.example.injectiontest.component.annotation

import dagger.hilt.DefineComponent

@PlayerViewScoped
@DefineComponent(parent = PlayerRetainedComponent::class)
interface PlayerViewComponent
