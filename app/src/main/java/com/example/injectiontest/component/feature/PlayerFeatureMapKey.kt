package com.example.injectiontest.component.feature

import dagger.MapKey

@MapKey
annotation class PlayerFeatureMapKey(
    /**
     * The value of the annotation.
     *
     * @return the key
     */
    val value: PlayerFeatureKey
)
