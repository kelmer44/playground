package com.example.injectiontest.archalt

import androidx.lifecycle.DefaultLifecycleObserver
import androidx.lifecycle.LifecycleOwner
import com.example.injectiontest.lobby.LobbyViewModel
import timber.log.Timber
import javax.inject.Inject

class ArchAltLifecycleObserver @Inject constructor(
    val viewModel: LobbyViewModel,
    private val archPresenter: ArchAltPresenter
) : DefaultLifecycleObserver {

    init {
        Timber.w("ArchAltLifecycleObserver created")
    }

    override fun onCreate(owner: LifecycleOwner) {
        super.onCreate(owner)
        Timber.d("ArchAltLifecycleObserver onCreate")

    }

    override fun onStart(owner: LifecycleOwner) {
        super.onStart(owner)
        Timber.i("ArchAltLifecycleObserver onStart")
    }
}
