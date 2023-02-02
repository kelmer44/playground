package com.example.injectiontest.component.annotation;

import com.example.injectiontest.component.experience.PlaybackExperience;
import com.example.injectiontest.component.experience.PlaybackExperienceKey;

import dagger.MapKey;

/**
 * Key to be used to bind {@link PlaybackExperience} into a map.
 */
@MapKey
public @interface PlaybackExperienceMapKey {
    /**
     * The value of the annotation.
     *
     * @return the key
     */
    PlaybackExperienceKey value();
}
