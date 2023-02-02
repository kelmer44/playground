package com.example.injectiontest.component.experience

import com.example.injectiontest.component.feature.PlayerFeatureKey

interface PlaybackExperience {
    val experienceKey: PlaybackExperienceKey
    val featureKeys: List<PlayerFeatureKey>

    object Kyber : PlaybackExperience {
        override val experienceKey: PlaybackExperienceKey = PlaybackExperienceKey.Kyber
        override val featureKeys: List<PlayerFeatureKey> = listOf(
            PlayerFeatureKey.TITLE, PlayerFeatureKey.SUBTITLE
        )
    }
}
