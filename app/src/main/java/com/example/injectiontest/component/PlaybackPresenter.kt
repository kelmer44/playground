package com.example.injectiontest.component

import androidx.fragment.app.Fragment
import com.example.injectiontest.component.experience.PlaybackExperience
import com.example.injectiontest.databinding.FragmentPlaybackBinding
import javax.inject.Inject

class PlaybackPresenter @Inject constructor(
    private val fragment: Fragment
) {

    private val binding = FragmentPlaybackBinding.bind(fragment.requireView())
    private val playbackView = binding.playbackView

    init {
        playbackView.setup(
            viewModelStoreOwner = fragment,
            lifecycleOwner = fragment.viewLifecycleOwner,
            playbackExperience = PlaybackExperience.Kyber
        )
    }
}
