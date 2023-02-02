package com.example.injectiontest.component.annotation

import javax.inject.Scope

/**
 * Scope to allow scoping instances in the [PlayerViewComponent].
 */
@Scope
@Retention(value = AnnotationRetention.RUNTIME)
annotation class PlayerViewScoped
