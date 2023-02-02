package com.example.injectiontest.component

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import com.example.injectiontest.R
import com.example.injectiontest.util.viewScoped
import dagger.hilt.android.AndroidEntryPoint
import timber.log.Timber
import javax.inject.Inject
import javax.inject.Provider

@AndroidEntryPoint
class PlaybackFragment : Fragment(R.layout.fragment_playback){

    @Inject
    internal lateinit var presenterProvider: Provider<PlaybackPresenter>

    private val presenter by viewScoped { presenterProvider.get() }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        Timber.d("Player Setup = OnViewCreated from $this")
        presenter
    }

    companion object {
        fun newInstance() = PlaybackFragment()
    }
}
