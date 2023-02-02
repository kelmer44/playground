package com.example.injectiontest.component.view

import android.content.Context
import android.view.LayoutInflater
import android.widget.TextView
import com.example.injectiontest.component.annotation.PlayerViewScoped
import com.example.injectiontest.component.feature.title.TitlesViews
import com.example.injectiontest.component.log.PlayerLog
import com.example.injectiontest.component.log.d
import com.example.injectiontest.databinding.KyberPlayerBinding
import javax.inject.Inject

@PlayerViewScoped
class KyberPlayerView @Inject constructor(
    private val playbackExperienceView: PlaybackExperienceView,
    private val playerLog: PlayerLog
) : PlayerView, TitlesViews {

    private val binding = KyberPlayerBinding.inflate(
        playbackExperienceView.context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater,
        playbackExperienceView
    )
    init {
        // This is for testing only, will remove later
        playerLog.d { "KyberPlayerView binding = ${binding != null}" }
    }

    override val title: TextView = binding.title
    override val subtitle: TextView = binding.subtitle
}
