package com.example.injectiontest.component.feature.title

import com.example.injectiontest.component.annotation.PlayerViewScoped
import javax.inject.Inject

@PlayerViewScoped
class TitlesPresenter @Inject constructor(
    private val views: TitlesViews,
){
    fun bindState(it: TitlesState) {
        views.title.text = it.titleData?.title
        views.subtitle.text = it.subtitleData?.subTitle
    }
}
