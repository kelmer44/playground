package com.example.injectiontest

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.injectiontest.injectedobjects.LobbyPresenter
import com.example.injectiontest.injectedobjects.LobbySheetPresenter
import com.example.injectiontest.injectedobjects.LobbyViewModel
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject
import javax.inject.Provider

@AndroidEntryPoint
class LobbyFragment : Fragment() {

    @Inject
    lateinit var lifecycleObserver: Provider<LobbyLifecycleObserver>

    @Inject
    lateinit var viewModel: LobbyViewModel

    @Inject
    lateinit var lobbyPresenter: LobbyPresenter

    @Inject
    lateinit var sheetPresenter: LobbySheetPresenter


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View =
        inflater.inflate(R.layout.fragment_main, container, false)


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewLifecycleOwner.lifecycle.addObserver(lifecycleObserver.get())
    }

    companion object {
        fun newInstance(chorradaId: String) = LobbyFragment().apply {
            this.arguments = Bundle().apply {
                this.putString("chorradaId", chorradaId)
            }
        }
    }
}
