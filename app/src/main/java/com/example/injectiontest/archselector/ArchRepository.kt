package com.example.injectiontest.archselector

import io.reactivex.Single
import io.reactivex.schedulers.Schedulers
import java.util.concurrent.TimeUnit
import javax.inject.Inject

class ArchRepository @Inject constructor() {
    private val characters = listOf("Mickey", "Minnie", "Donald", "Daisy", "Goofy", "Pluto")

    /**
     * Load the Disney Characters with an artificial delay.
     */
    fun charactersOnce(): Single<List<String>> =
        Single.just(characters)
            .delay(DELAY, TimeUnit.MILLISECONDS, Schedulers.io())

    companion object {
        private const val DELAY = 2000L
    }
}
