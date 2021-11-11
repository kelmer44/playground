package com.example.injectiontest.lobby

import android.graphics.Color
import androidx.fragment.app.Fragment
import javax.inject.Inject
import android.view.View
import com.example.app_annotations.FragmentArgument
import com.example.injectiontest.lobby.LobbyFragment.Companion.INTEGER_PARAM_KEY
import com.example.injectiontest.lobby.LobbyFragment.Companion.PARCELABLE_PARAM_KEY
import com.example.injectiontest.lobby.LobbyFragment.Companion.STRING_PARAM_KEY
import com.example.injectiontest.R
import com.example.injectiontest.databinding.FragmentLobbyBinding
import com.example.injectiontest.model.ParamHolder
import com.example.injectiontest.view.ColorBox
import timber.log.Timber
import javax.inject.Named

class LobbyPresenter @Inject constructor(
    private val fragment: Fragment,
    private val viewModel: LobbyViewModel,
    @Named(STRING_PARAM_KEY)
    @FragmentArgument
    private val injectedParam: String,
    @Named(PARCELABLE_PARAM_KEY)
    @FragmentArgument
    private val injectedParamHolder: ParamHolder,
    @Named(INTEGER_PARAM_KEY)
    @FragmentArgument
    private val injectedInteger: Int
) {

    private val binding = FragmentLobbyBinding.bind(fragment.requireView())

    init {
        Timber.i("InjectedStringParam=$injectedParam, InjectedParcelable=$injectedParamHolder (from $this), InjectedInt = $injectedInteger")
    }

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
