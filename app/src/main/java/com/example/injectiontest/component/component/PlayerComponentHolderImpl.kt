package com.example.injectiontest.component.component

import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.ViewModelStoreOwner
import com.example.injectiontest.component.annotation.PlayerViewComponent
import com.example.injectiontest.component.experience.PlaybackExperience
import com.example.injectiontest.component.log.d
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject
import dagger.hilt.EntryPoints
import timber.log.Timber

class PlayerComponentHolderImpl @AssistedInject internal constructor(
    builder: RetainedComponent.Builder,
    @Assisted viewModelStoreOwner: ViewModelStoreOwner,
    @Assisted lifecycleOwner: LifecycleOwner,
    @Assisted experience: PlaybackExperience,
) : PlayerComponentHolder {

    private val retainedComponent = RetainedComponent.playerRetainedComponent(
        viewModelStoreOwner,
        builder = builder,
        experience = experience
    )

    private val viewComponent : PlayerViewComponent =
        getRetainedEntryPoint<RetainedComponent.EntryPoint>().viewComponentBuilder
            .seedLifecycleOwner(lifecycleOwner)
            .seedViewModelStoreOwner(viewModelStoreOwner)
            .build()

    init {
        registerFeatures(experience)
        Timber.w("Player Setup --- retainedComponent is = $retainedComponent, viewComponent is = $viewComponent")
    }

    private fun registerFeatures(experience: PlaybackExperience) {
        val entryPoint = getViewEntryPoint<ViewComponent.EntryPoint>()
        val logger = entryPoint.logger
        val featureKeys = experience.featureKeys
        for(featureKey in featureKeys){
            val playerFeatureProvider = entryPoint.playerFeatureKeyProviders[featureKey]
            if (playerFeatureProvider == null) {
                logger.d { "No PlayerFeature was bound into the map for: $featureKey" }
                continue
            }
            playerFeatureProvider.get()
        }

    }

    override fun <T : Any> getViewEntryPoint(clazz: Class<T>): T = EntryPoints.get(viewComponent, clazz)

    override fun <T : Any> getRetainedEntryPoint(clazz: Class<T>): T = EntryPoints.get(retainedComponent, clazz)

    @AssistedFactory
    interface Factory : PlayerComponentHolder.Factory {

        override fun create(
            viewModelStoreOwner: ViewModelStoreOwner,
            lifecycleOwner: LifecycleOwner,
            playbackExperience: PlaybackExperience,
        ): PlayerComponentHolderImpl
    }
}
