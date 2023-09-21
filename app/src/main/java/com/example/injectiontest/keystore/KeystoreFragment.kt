package com.example.injectiontest.keystore

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import com.example.injectiontest.R
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject
import javax.inject.Provider

@AndroidEntryPoint
class KeystoreFragment : Fragment(R.layout.fragment_keystore) {

    @Inject
    lateinit var lifecycleObserver: Provider<KeystoreLifecycleObserver>

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewLifecycleOwner.lifecycle.addObserver(lifecycleObserver.get())
    }

    companion object {
        fun newInstance() = KeystoreFragment().also {
            it.arguments = Bundle()
        }
    }
}