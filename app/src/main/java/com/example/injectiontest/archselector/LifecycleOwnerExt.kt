package com.example.injectiontest.archselector.archalt

import androidx.lifecycle.DefaultLifecycleObserver
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleOwner
import com.uber.autodispose.android.lifecycle.scope
import com.uber.autodispose.autoDisposable
import io.reactivex.Flowable
import io.reactivex.android.schedulers.AndroidSchedulers

/**
* Convenience method for observing a [Flowable] instance while the [LifecycleOwner] is at least started.
*/
inline fun <T : Any> LifecycleOwner.subscribeWhileStarted(flowable: Flowable<T>, crossinline onNext: (T) -> Unit) {
    lifecycle.addObserver(
        object : DefaultLifecycleObserver {
            override fun onStart(owner: LifecycleOwner) {
                flowable.observeOn(AndroidSchedulers.mainThread())
                    .autoDisposable(owner.scope(Lifecycle.Event.ON_STOP))
                    .subscribe({ onNext.invoke(it) }, {})
            }
        }
    )
}
