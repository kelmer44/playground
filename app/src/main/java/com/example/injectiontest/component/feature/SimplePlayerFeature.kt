package com.example.injectiontest.component.feature

import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.LifecycleOwner
import com.example.injectiontest.component.log.PlayerLog
import com.example.injectiontest.component.log.d
import com.example.injectiontest.component.annotation.PlayerManaged
import com.example.injectiontest.component.annotation.PlayerViewScoped
import javax.inject.Inject

/**
 * Implementation of [PlayerFeature] which just instantiates an instance of [T] when the player is created and on every
 * configuration change. If [T] is a [LifecycleObserver] it will be added to the [PlayerManaged] [LifecycleOwner].
 */
@PlayerViewScoped
class SimplePlayerFeature<T : Any> @Inject constructor(
    @PlayerManaged lifecycleOwner: LifecycleOwner,
    featureEntryPoint: T,
    playerLog: PlayerLog,
) : PlayerFeature {

    init {
        if (featureEntryPoint is LifecycleObserver) {
            playerLog.d { "Adding lifecycle observer: ${featureEntryPoint::class.simpleName}" }
            lifecycleOwner.lifecycle.addObserver(featureEntryPoint)
        }
        playerLog.d { "(Re)-initialized feature with instance: ${featureEntryPoint::class.simpleName}" }
    }
}
