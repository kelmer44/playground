package com.example.injectiontest.archselector.archalt

import androidx.core.view.isVisible
import com.example.injectiontest.databinding.FragmentArchBinding
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject
import timber.log.Timber
import javax.inject.Inject

class ArchAltPresenter @AssistedInject constructor(
    @Assisted private val binding: FragmentArchBinding
) {

    init {
        Timber.v("ArchAltPresenter created")
    }

    fun bindState(state: ArchAltViewModel.BasicState){
        binding.basicLoadingView.isVisible = state.isLoading

        binding.basicCharactersText.isVisible = !state.isLoading && state.characters.isNotEmpty()
        binding.basicCharactersText.text = state.characters.joinToString()

        binding.basicRetryBtn.isVisible = state.error != null
    }

    @AssistedFactory
    internal interface Factory {
        fun create(binding: FragmentArchBinding): ArchAltPresenter
    }
}
