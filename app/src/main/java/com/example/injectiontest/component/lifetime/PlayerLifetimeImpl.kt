package com.example.injectiontest.component.lifetime

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelStoreOwner
import com.example.injectiontest.component.annotation.PlayerRetainedScoped
import com.example.injectiontest.util.ViewModelUtils
import com.uber.autodispose.ScopeProvider
import dagger.hilt.android.scopes.ActivityScoped
import io.reactivex.Flowable
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.flowables.ConnectableFlowable
import io.reactivex.functions.Action
import io.reactivex.subjects.CompletableSubject
import javax.inject.Inject

@ActivityScoped
class PlayerLifetimeImpl @Inject constructor(
) : PlayerLifetime {

    private val disposable = CompositeDisposable()
    private val completableSubject = CompletableSubject.create()

    override val scope: ScopeProvider = ScopeProvider { completableSubject }

    override fun <T : Any> autoConnect(
        connectableFlowable: ConnectableFlowable<T>, numberOfSubscribers: Int
    ): Flowable<T> = connectableFlowable.autoConnect(numberOfSubscribers) { disposable.add(it) }

    fun setup(viewModelStoreOwner: ViewModelStoreOwner) {
        ViewModelUtils.getViewModel(viewModelStoreOwner, Disposer::class.java) { Disposer(this) }
    }

    internal class Disposer(private val playerLifetime: PlayerLifetimeImpl) : ViewModel() {
        override fun onCleared() {
            playerLifetime.disposable.clear()
            playerLifetime.completableSubject.onComplete()
            super.onCleared()
        }
    }
}
