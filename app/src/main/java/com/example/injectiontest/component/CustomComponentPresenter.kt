package com.example.injectiontest.component

import androidx.fragment.app.Fragment
import com.example.injectiontest.databinding.FragmentCustomcomponentBinding
import javax.inject.Inject

class CustomComponentPresenter @Inject constructor(
    private val fragment: Fragment
) {

    private val binding = FragmentCustomcomponentBinding.bind(fragment.requireView())
}
