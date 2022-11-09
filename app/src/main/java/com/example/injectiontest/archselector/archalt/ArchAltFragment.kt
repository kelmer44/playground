package com.example.injectiontest.archselector.archalt

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import com.example.injectiontest.R
import com.example.injectiontest.databinding.FragmentArchBinding
import dagger.hilt.android.AndroidEntryPoint
import timber.log.Timber
import javax.inject.Inject
import javax.inject.Provider

@AndroidEntryPoint
class ArchAltFragment : Fragment(R.layout.fragment_arch) {

    @Inject
    internal lateinit var presenterFactory: ArchAltPresenter.Factory

    @Inject
    lateinit var viewModel: ArchAltViewModel

    private val presenter by viewScoped { presenterFactory.create(FragmentArchBinding.bind(it)) }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewLifecycleOwner.subscribeWhileStarted(viewModel.stateOnceAndStream) {
            presenter?.bindState(it)
        }
    }

    companion object {
        fun newInstance() = ArchAltFragment()
    }
}
