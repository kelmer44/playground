package com.example.injectiontest.flow

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import com.example.injectiontest.R
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject
import javax.inject.Provider

@AndroidEntryPoint
class FlowFragment : Fragment(R.layout.fragment_flow) {

    @Inject
    lateinit var lifecycleObserver: Provider<FlowLifecycleObserver>

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewLifecycleOwner.lifecycle.addObserver(lifecycleObserver.get())
    }

    companion object {
        fun newInstance() = FlowFragment().also {
            it.arguments = Bundle()
        }
    }

}
