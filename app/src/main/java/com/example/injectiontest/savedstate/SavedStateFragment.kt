package com.example.injectiontest.savedstate

import androidx.fragment.app.Fragment
import com.example.injectiontest.R
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class SavedStateFragment : Fragment(R.layout.fragment_lobby) {

    @Inject
    lateinit var viewModel: SavedStateViewModel

}
