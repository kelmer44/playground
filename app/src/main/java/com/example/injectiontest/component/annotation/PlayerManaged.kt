package com.example.injectiontest.component.annotation

import javax.inject.Qualifier

/**
 * Qualifier for injecting the [androidx.lifecycle.ViewModelStoreOwner] and [androidx.lifecycle.LifecycleOwner] of the
 * player.
 */
@Qualifier
@Retention(AnnotationRetention.RUNTIME)
annotation class PlayerManaged
