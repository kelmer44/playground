package com.example.injectiontest.savedstate

import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import androidx.savedstate.SavedStateRegistryOwner
import com.example.injectiontest.R
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class SavedStateFragment : Fragment(R.layout.fragment_saved) {
//
//    @Inject
//    internal lateinit var savedStateViewModelFactory: SavedStateViewModelFactory

    @Inject
    lateinit var store: CustomViewModelStore

    private val viewModel: SavedStateViewModel by viewModels(ownerProducer = { store })

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel.hola()
    }

//    override fun getDefaultViewModelProviderFactory(): ViewModelProvider.Factory {
//        return GenericSavedStateViewModelFactory(savedStateViewModelFactory, this)
//    }
}
