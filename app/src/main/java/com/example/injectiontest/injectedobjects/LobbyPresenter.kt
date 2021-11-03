package com.example.injectiontest.injectedobjects

import androidx.fragment.app.Fragment
import javax.inject.Inject
import android.view.View
import android.widget.EditText
import android.widget.TextView
import com.example.app_annotations.FragmentArgument
import com.example.injectiontest.LobbyFragment.Companion.PARAM_KEY
import com.example.injectiontest.R
import timber.log.Timber
import javax.inject.Named

class LobbyPresenter @Inject constructor(
    private val fragment: Fragment,
    private val viewModel: LobbyViewModel,
    @Named("injection_param")
    @FragmentArgument
    private val injectedParam: String
) {

    private val containerView: View? get() = fragment.view

//    @FragmentArgument
//    @Named("injection_param")
    val injectedField: String = fragment.arguments?.getString(PARAM_KEY) ?: ""

    init {
        Timber.i("InjectedField=$injectedField, InjectedParam=$injectedParam (from $this)")
    }

    fun doSomething() {
        containerView?.findViewById<TextView>(R.id.hello)?.text = "Hello from $this"
        containerView?.findViewById<TextView>(R.id.button)?.setOnClickListener {
            viewModel.useHelper()
        }
        containerView?.findViewById<TextView>(R.id.button_router)?.setOnClickListener {
            viewModel.triggerContentRouter()
        }

        containerView?.findViewById<EditText>(R.id.input)?.setOnFocusChangeListener { v, hasFocus ->
            Timber.i("Focus change on disneyinput $hasFocus (view is $v)")
        }
    }
}
