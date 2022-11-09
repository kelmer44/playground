package com.example.injectiontest.archselector.arch

import androidx.lifecycle.DefaultLifecycleObserver
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleOwner
import com.example.injectiontest.flow.lobby.LobbyViewModel
import com.uber.autodispose.android.lifecycle.scope
import com.uber.autodispose.autoDisposable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import org.jetbrains.annotations.Async.Schedule
import timber.log.Timber
import javax.inject.Inject

class ArchLifecycleObserver @Inject constructor(
    val viewModel: ArchViewModel,
    private val archPresenter: ArchPresenter
) : DefaultLifecycleObserver {

    init {
        Timber.w("ArchLifecycleObserver created")
    }

    override fun onCreate(owner: LifecycleOwner) {
        super.onCreate(owner)
        Timber.d("ArchLifecycleObserver onCreate")

    }

    override fun onStart(owner: LifecycleOwner) {
        super.onStart(owner)
        Timber.i("ArchLifecycleObserver onStart")
        viewModel.stateOnceAndStream
            .observeOn(AndroidSchedulers.mainThread())
            .autoDisposable(owner.scope(Lifecycle.Event.ON_STOP))
            .subscribe({
                       this.archPresenter.bindState(it)
            },{

            })
    }
}
