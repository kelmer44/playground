package com.example.injectiontest.archselector.arch

import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import com.example.injectiontest.archselector.archalt.ArchAltViewModel
import com.example.injectiontest.databinding.FragmentArchBinding
import timber.log.Timber
import javax.inject.Inject

class ArchPresenter @Inject constructor(
    private val fragment: Fragment,
    private val viewModel: ArchViewModel
) {
    private val binding = FragmentArchBinding.bind(fragment.requireView())

    init {
        Timber.v("ArchPresenter created")
    }

    fun bindState(state: ArchViewModel.BasicState){
        binding.basicLoadingView.isVisible = state.isLoading

        binding.basicCharactersText.isVisible = !state.isLoading && state.characters.isNotEmpty()
        binding.basicCharactersText.text = state.characters.joinToString()

        binding.basicRetryBtn.isVisible = state.error != null
    }

}
