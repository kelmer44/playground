package com.example.injectiontest.injectedobjects

import android.view.View
import android.widget.Button
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.example.injectiontest.R
import javax.inject.Inject

class LobbySheetPresenter @Inject constructor(
    private val viewModel: LobbyViewModel,
    private val fragment: Fragment,
    private val leaveHelper: LobbyLeaveHelper
) {

    private val containerView: View? get() = fragment.view

    private fun setupLeaveButton() {
        containerView?.findViewById<TextView>(R.id.leaveGroupSheetButton)
            ?.setOnClickListener { leaveHelper.showLeaveConfirmationAlertDialog() }
    }

    fun init() {
        setupLeaveButton()
    }


}
