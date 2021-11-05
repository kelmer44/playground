package com.example.injectiontest

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.injectiontest.injectedobjects.LobbySheetPresenter
import com.example.injectiontest.injectedobjects.LobbyViewModel
import com.example.injectiontest.model.ParamHolder
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
    lateinit var sheetPresenter: LobbySheetPresenter


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View =
        inflater.inflate(R.layout.fragment_main, container, false)


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewLifecycleOwner.lifecycle.addObserver(lifecycleObserver.get())
    }

    companion object {
        const val STRING_PARAM_KEY = "string_injection_param"
        const val PARCELABLE_PARAM_KEY = "parcelable_injection_param"
        fun newInstance(chorradaId: String, param: ParamHolder) = LobbyFragment().apply {
            this.arguments = Bundle().apply {
                this.putString(STRING_PARAM_KEY, chorradaId)
                this.putParcelable(PARCELABLE_PARAM_KEY, param)
            }
        }
    }
}
