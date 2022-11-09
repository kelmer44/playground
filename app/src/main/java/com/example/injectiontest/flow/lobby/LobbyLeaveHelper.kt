package com.example.injectiontest.flow.lobby

import com.example.injectiontest.hexCode
import timber.log.Timber
import javax.inject.Inject

class LobbyLeaveHelper @Inject constructor(
    private val viewModel: LobbyViewModel,
//    private val viewModelProvider: Lazy<LobbyViewModel>,
    private val contentTypeRouter: ContentTypeRouter
) {

//    private val viewModel
//        get() = viewModelProvider.get()

    init {
        Timber.d("ONJECT - Instantiating LobbyHelper ${this.hexCode()}")
    }

    fun showLeaveConfirmationAlertDialog() {
        Timber.i("ONJECT - Printing ViewModel [${viewModel.hexCode()}] from LobbyHelper =[${this.hexCode()}]")
        Timber.v("ONJECT - ContentRouter is ${contentTypeRouter.hexCode()}")
    }


    fun print() {
        Timber.i("LobbyLeaveHelper called!")
    }

    fun onLeftSession() {
        routeToContentDetails()
    }

    private fun routeToContentDetails() {
        Timber.d("ONJECT - Calling from LobbyHelper [${this.hexCode()}] to contentRouter where router is [${contentTypeRouter.hexCode()}]")
        contentTypeRouter.doStuff()
    }

}
