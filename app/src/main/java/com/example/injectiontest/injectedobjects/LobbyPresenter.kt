package com.example.injectiontest.injectedobjects

import androidx.fragment.app.Fragment
import javax.inject.Inject
import android.view.View
import android.widget.EditText
import android.widget.TextView
import com.example.injectiontest.R
import timber.log.Timber

class LobbyPresenter @Inject constructor(
    private val fragment: Fragment,
    private val viewModel: LobbyViewModel
) {

    private val containerView: View? get() = fragment.view

    init {

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
