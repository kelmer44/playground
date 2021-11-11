package com.example.injectiontest.lobby

import androidx.lifecycle.DefaultLifecycleObserver
import androidx.lifecycle.LifecycleOwner
import javax.inject.Inject

class LobbyLifecycleObserver @Inject constructor(
    val viewModel: LobbyViewModel,
    private val lobbyPresenter: LobbyPresenter,
    private val lobbySheetPresenter: LobbySheetPresenter
) : DefaultLifecycleObserver{

    override fun onCreate(owner: LifecycleOwner) {
        super.onCreate(owner)
        lobbySheetPresenter.init()
        lobbyPresenter.doSomething()
    }

    override fun onStart(owner: LifecycleOwner) {
        super.onStart(owner)
    }

    override fun onStop(owner: LifecycleOwner) {
        super.onStop(owner)
    }
}
