package com.example.injectiontest.archselector

import androidx.lifecycle.ViewModel
import io.reactivex.Flowable
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.flowables.ConnectableFlowable

open class AutoDisposeViewModel : ViewModel() {

    /**
     * [CompositeDisposable] which will be disposed when the [ViewModel.onCleared] is called.
     */
    protected val viewModelDisposable = CompositeDisposable()

    /**
     * Extension method on [ConnectableFlowable] which calls [ConnectableFlowable.connect] and ensures that the
     * connection is disposed when [ViewModel.onCleared] is invoked.
     */
    protected fun <T : Any> ConnectableFlowable<T>.connectInViewModelScope(): Flowable<T> {
        return autoConnect(1) { viewModelDisposable.add(it) }
    }

    override fun onCleared() {
        viewModelDisposable.dispose()
        super.onCleared()
    }
}
