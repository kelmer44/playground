package com.example.injectiontest.component.feature.title

import androidx.lifecycle.DefaultLifecycleObserver
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleOwner
import com.example.injectiontest.component.log.PlayerLog
import com.example.injectiontest.component.log.d
import com.example.injectiontest.component.log.e
import com.uber.autodispose.android.lifecycle.scope
import com.uber.autodispose.autoDisposable
import io.reactivex.android.schedulers.AndroidSchedulers
import javax.inject.Inject

class TitlesLifecycleObserver @Inject constructor(
    private val presenter: TitlesPresenter, private val viewModel: TitlesViewModel, private val playerLog: PlayerLog
) : DefaultLifecycleObserver {

    override fun onStart(owner: LifecycleOwner) {
        viewModel.stateOnceAndStream.observeOn(AndroidSchedulers.mainThread())
            .autoDisposable(owner.scope(Lifecycle.Event.ON_STOP)).subscribe(
                {
                    presenter.bindState(it)
                    playerLog.d { "TitlesLifecycleObserver:onStart - TitlesPresenter bound" }

                },
                {
                    playerLog.e { "Error when observing TitlesViewModel.stateOnceAndStream" }
                }
            )
    }
}
