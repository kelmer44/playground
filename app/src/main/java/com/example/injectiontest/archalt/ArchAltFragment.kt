package com.example.injectiontest.archalt

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import com.example.injectiontest.R
import dagger.hilt.android.AndroidEntryPoint
import timber.log.Timber
import javax.inject.Inject
import javax.inject.Provider

@AndroidEntryPoint
class ArchAltFragment : Fragment(R.layout.fragment_arch) {

    @Inject
    lateinit var provider: Provider<ArchAltLifecycleObserver>

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        Timber.i("ArchAltFragment OnViewCreated")
        viewLifecycleOwner.lifecycle.addObserver(provider.get())
    }
}
