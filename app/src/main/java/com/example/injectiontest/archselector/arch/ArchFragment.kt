package com.example.injectiontest.archselector.arch

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import com.example.injectiontest.R
import dagger.hilt.android.AndroidEntryPoint
import timber.log.Timber
import javax.inject.Inject
import javax.inject.Provider

@AndroidEntryPoint
class ArchFragment : Fragment(R.layout.fragment_arch) {

    @Inject
    lateinit var provider: Provider<ArchLifecycleObserver>

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        Timber.i("ArchFragment OnViewCreated")
        viewLifecycleOwner.lifecycle.addObserver(provider.get())
    }

    companion object {
        fun newInstance() = ArchFragment()
    }
}
