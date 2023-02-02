package com.example.injectiontest.component.lifetime

import com.uber.autodispose.ScopeProvider
import io.reactivex.Flowable
import io.reactivex.flowables.ConnectableFlowable
import io.reactivex.functions.Action

interface PlayerLifetime {
    val scope: ScopeProvider
    fun <T : Any> autoConnect(connectableFlowable: ConnectableFlowable<T>, numberOfSubscribers: Int = 1): Flowable<T>
}

fun <T : Any> ConnectableFlowable<T>.autoConnectIn(
    lifetime: PlayerLifetime,
    numberOfSubscribers: Int = 1,
) = lifetime.autoConnect(connectableFlowable = this, numberOfSubscribers)
