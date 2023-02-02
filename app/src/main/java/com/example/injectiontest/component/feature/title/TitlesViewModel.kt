package com.example.injectiontest.component.feature.title

import com.example.injectiontest.component.annotation.PlayerRetainedScoped
import com.example.injectiontest.component.lifetime.PlayerLifetime
import com.example.injectiontest.component.lifetime.autoConnectIn
import io.reactivex.Flowable
import io.reactivex.schedulers.Schedulers
import java.util.concurrent.TimeUnit
import javax.inject.Inject

@PlayerRetainedScoped
class TitlesViewModel @Inject constructor(
    lifetime: PlayerLifetime
){
    val stateOnceAndStream: Flowable<TitlesState> =
        getTitleData()
            .replay(1)
            .autoConnectIn(lifetime)

    private fun getTitleData(): Flowable<TitlesState> = Flowable.just(TitlesState(
            "What is playing",
            TitlesState.TitleData("My content", "My content description"),
            TitlesState.SubtitleData("My subtitle", "My fancy subtitle")
        )).delay(500L, TimeUnit.MILLISECONDS, Schedulers.computation())
}
