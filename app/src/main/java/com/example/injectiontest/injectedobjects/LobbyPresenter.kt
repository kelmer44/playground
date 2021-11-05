package com.example.injectiontest.injectedobjects

import androidx.fragment.app.Fragment
import javax.inject.Inject
import android.view.View
import android.widget.EditText
import android.widget.TextView
import com.example.app_annotations.FragmentArgument
import com.example.injectiontest.LobbyFragment.Companion.PARCELABLE_PARAM_KEY
import com.example.injectiontest.LobbyFragment.Companion.STRING_PARAM_KEY
import com.example.injectiontest.R
import com.example.injectiontest.model.ParamHolder
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
    private val injectedParamHolder: ParamHolder
) {

    private val containerView: View? get() = fragment.view

    init {
        Timber.i("InjectedStringParam=$injectedParam, InjectedParcelable=$injectedParamHolder (from $this)")
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
