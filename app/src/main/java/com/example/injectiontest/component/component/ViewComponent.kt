package com.example.injectiontest.component.component

import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.ViewModelStoreOwner
import com.example.injectiontest.component.annotation.PlayerManaged
import com.example.injectiontest.component.annotation.PlayerViewComponent
import com.example.injectiontest.component.feature.PlayerFeature
import com.example.injectiontest.component.feature.PlayerFeatureKey
import com.example.injectiontest.component.log.PlayerLog
import dagger.BindsInstance
import dagger.hilt.DefineComponent
import dagger.hilt.InstallIn
import javax.inject.Provider

internal object ViewComponent {

    @DefineComponent.Builder
    internal interface Builder {
        fun seedViewModelStoreOwner(@BindsInstance @PlayerManaged owner: ViewModelStoreOwner) : Builder
        fun seedLifecycleOwner(@BindsInstance @PlayerManaged owner: LifecycleOwner) : Builder
        fun build() : PlayerViewComponent
    }

    @dagger.hilt.EntryPoint
    @InstallIn(PlayerViewComponent::class)
    internal interface EntryPoint {

        val playerFeatureKeyProviders: Map<PlayerFeatureKey, Provider<PlayerFeature>>
        val logger: PlayerLog
    }
}
