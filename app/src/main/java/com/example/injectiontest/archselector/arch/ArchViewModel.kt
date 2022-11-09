package com.example.injectiontest.archselector.arch

import androidx.lifecycle.ViewModel
import com.example.injectiontest.archselector.ArchRepository
import com.example.injectiontest.archselector.AutoDisposeViewModel
import io.reactivex.Flowable
import io.reactivex.Single
import io.reactivex.processors.BehaviorProcessor
import timber.log.Timber

class ArchViewModel(private val repository: ArchRepository) : AutoDisposeViewModel() {

    private val loadTrigger = BehaviorProcessor.createDefault(Unit)

    init {
        Timber.i("ArchAltViewModel created")
    }

    /**
     * Provides a stream of states which the presenter can evaluate and update the UI
     */
    val stateOnceAndStream: Flowable<BasicState> =
        loadTrigger.switchMapSingle { stateOnce() }
            .startWith(BasicState(isLoading = true))
            .distinctUntilChanged()
            .replay(1)
            .connectInViewModelScope()

    private fun stateOnce(): Single<BasicState> =
        repository.charactersOnce()
            .map { BasicState(characters = it) }
            .onErrorReturn { BasicState(error = it) }

    /**
     * ViewModel state
     */
    data class BasicState(
        val characters: List<String> = emptyList(),
        val isLoading: Boolean = false,
        val error: Throwable? = null,
    )

}
