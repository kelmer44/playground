package com.example.injectiontest.hiltviewmodel

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.example.injectiontest.R
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HVMFragment : Fragment(R.layout.fragment_hilt) {

    private val hmvViewModel: HMVViewModel by viewModels()


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        hmvViewModel.hello()
    }

    fun newInstance(): HVMFragment {
        return HVMFragment()
    }
}
