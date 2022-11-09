package com.example.injectiontest.arch

import androidx.lifecycle.DefaultLifecycleObserver
import androidx.lifecycle.LifecycleOwner
import com.example.injectiontest.lobby.LobbyViewModel
import timber.log.Timber
import javax.inject.Inject

class ArchLifecycleObserver @Inject constructor(
    val viewModel: LobbyViewModel,
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
    }
}
