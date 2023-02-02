package com.example.injectiontest.component.view

import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.ViewModelStoreOwner
import com.example.injectiontest.component.experience.PlaybackExperience
import com.example.injectiontest.component.component.PlayerComponentHolder
import javax.inject.Inject

class PlaybackExperiencePresenter @Inject constructor(
//    private var activity: FragmentActivity,
    private var playerComponentHolderFactory: PlayerComponentHolder.Factory
) : PlaybackExperienceView.Presenter {
    override lateinit var playerComponentHolder: PlayerComponentHolder

    override fun setup(
        viewModelStoreOwner: ViewModelStoreOwner,
        lifecycleOwner: LifecycleOwner,
        playbackExperience: PlaybackExperience
    ) {
        playerComponentHolder = playerComponentHolderFactory.create(
            viewModelStoreOwner,
            lifecycleOwner,
            playbackExperience,
//            activity
        )
    }
}
