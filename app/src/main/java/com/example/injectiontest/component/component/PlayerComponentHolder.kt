package com.example.injectiontest.component.component

import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.ViewModelStoreOwner
import com.example.injectiontest.component.experience.PlaybackExperience

interface PlayerComponentHolder {

    fun <T : Any> getViewEntryPoint(clazz: Class<T>): T

    fun <T : Any> getRetainedEntryPoint(clazz: Class<T>): T

    interface Factory {
        /**
         * See docs on implementation of [PlayerComponentHolder] for more details about these.
         */
        fun create(
            viewModelStoreOwner: ViewModelStoreOwner,
            lifecycleOwner: LifecycleOwner,
            playbackExperience: PlaybackExperience,
        ): PlayerComponentHolder
    }
}

/**
 * Convenience method for [PlayerComponentHolder.getViewEntryPoint].
 */
inline fun <reified T : Any> PlayerComponentHolder.getViewEntryPoint() = getViewEntryPoint(T::class.java)

/**
 * Convenience method for [PlayerComponentHolder.getRetainedEntryPoint].
 */
inline fun <reified T : Any> PlayerComponentHolder.getRetainedEntryPoint() = getRetainedEntryPoint(T::class.java)
