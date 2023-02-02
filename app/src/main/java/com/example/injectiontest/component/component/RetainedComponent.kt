package com.example.injectiontest.component.component

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelStoreOwner
import com.example.injectiontest.component.annotation.PlayerRetainedComponent
import com.example.injectiontest.component.experience.PlaybackExperience
import dagger.BindsInstance
import dagger.hilt.DefineComponent
import dagger.hilt.InstallIn

internal object RetainedComponent {

    @DefineComponent.Builder
    interface Builder {
        fun seedPlaybackExperience(@BindsInstance playbackExperience: PlaybackExperience): Builder
        fun build(): PlayerRetainedComponent
    }

    class Holder(
        builder: Builder,
        experience: PlaybackExperience
    ) : ViewModel() {
        val retainedComponent = builder
            .seedPlaybackExperience(experience)
            .build()
    }

    fun playerRetainedComponent(
        viewModelStoreOwner: ViewModelStoreOwner,
        builder: Builder,
        experience: PlaybackExperience
    ) = ViewModelProvider(
        viewModelStoreOwner,
        object: ViewModelProvider.Factory {
            override fun <T : ViewModel> create(modelClass: Class<T>): T =
                RetainedComponent.Holder(builder, experience) as T
        })[RetainedComponent.Holder::class.java].retainedComponent

    @dagger.hilt.EntryPoint
    @InstallIn(PlayerRetainedComponent::class)
    internal interface EntryPoint {
        val viewComponentBuilder: ViewComponent.Builder
    }
}
