package com.example.injectiontest.component.feature.title

import androidx.fragment.app.FragmentActivity
import com.example.injectiontest.component.annotation.PlayerViewScoped
import com.example.injectiontest.component.log.PlayerLog
import com.example.injectiontest.component.log.e
import javax.inject.Inject

@PlayerViewScoped
class TitlesPresenter @Inject constructor(
    private val activity: FragmentActivity,
    private val views: TitlesViews,
    private val playerLog: PlayerLog
){

    init {
        playerLog.e { "Player Setup = activity is $activity" }
    }
    fun bindState(it: TitlesState) {
        views.title.text = it.titleData?.title
        views.subtitle.text = it.subtitleData?.subTitle
    }
}
