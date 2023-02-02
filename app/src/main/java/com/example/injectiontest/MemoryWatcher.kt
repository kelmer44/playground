package com.example.injectiontest

import android.os.Handler
import androidx.fragment.app.FragmentActivity
import timber.log.Timber
import java.lang.ref.WeakReference
import java.util.concurrent.Executor
import java.util.concurrent.Executors
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class MemoryWatcher @Inject constructor() {
    
    private val references = mutableListOf<WeakReference<FragmentActivity>>()
    
    fun addReference(activity: FragmentActivity){
        references.add(WeakReference(activity))
        Timber.i("Player Setup = adding $activity, total references = ${references.size}")

    }
    
    init {
//        val executor: Executor = Executors.newSingleThreadExecutor()
//        executor.execute(Runnable {
//            while (true) {
//                Timber.i("Player Setup = references = ${references.map { it.get() }}")
//                Thread.sleep(1000);
//            }
//        })
    }

}
