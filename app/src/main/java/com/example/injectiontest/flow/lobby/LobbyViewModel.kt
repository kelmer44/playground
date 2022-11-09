package com.example.injectiontest.flow.lobby

import androidx.lifecycle.ViewModel
import com.example.injectiontest.hexCode
import dagger.Lazy
import timber.log.Timber

class LobbyViewModel(
    private val leaveHelperProvider: Lazy<LobbyLeaveHelper>,
) : ViewModel() {

    private val leaveHelper
        get() = leaveHelperProvider.get()

    fun useHelper() {
        Timber.w("ONJECT - UsingHelper from viewModel [${this.hexCode()}]: leaveHelper: [${leaveHelper.hexCode()}]")
        leaveHelper.print()
    }


    private val cosa = State("My thing")

    fun getStuff() {
        Timber.d("ONJECT - ViewModel [${this.hexCode()}] printing $cosa")
    }

    fun triggerContentRouter() {
        Timber.e("ONJECT -  Will trigger now from VM [${this.hexCode()}] to router call on LeaveHelper [${leaveHelper.hexCode()}]")
        leaveHelper.onLeftSession()

    }

    data class State(
        val name: String
    )
}
