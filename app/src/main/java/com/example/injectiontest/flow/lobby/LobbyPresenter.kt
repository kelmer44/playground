package com.example.injectiontest.flow.lobby

import androidx.fragment.app.Fragment
import javax.inject.Inject
import com.example.injectiontest.databinding.FragmentLobbyBinding
import timber.log.Timber

class LobbyPresenter @Inject constructor(
    private val fragment: Fragment,
    private val viewModel: LobbyViewModel,
) {

    private val binding = FragmentLobbyBinding.bind(fragment.requireView())

    fun doSomething() {
        binding.hello.text = "Hello from $this"
        binding.button.setOnClickListener {
            viewModel.useHelper()
        }
        binding.buttonRouter.setOnClickListener {
            viewModel.triggerContentRouter()
        }

        binding.input.setOnFocusChangeListener { v, hasFocus ->
            Timber.i("Focus change on disneyinput $hasFocus (view is $v)")
        }

    }
}
