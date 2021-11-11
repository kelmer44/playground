package com.example.injectiontest.flow

import androidx.lifecycle.DefaultLifecycleObserver
import androidx.lifecycle.LifecycleOwner
import com.example.injectiontest.lobby.LobbyViewModel
import javax.inject.Inject

class FlowLifecycleObserver @Inject constructor(
    val viewModel: FlowViewModel,
    private val flowPresenter: FlowPresenter
) : DefaultLifecycleObserver {

    override fun onCreate(owner: LifecycleOwner) {
        super.onCreate(owner)
        flowPresenter.doSomething()
    }
}
