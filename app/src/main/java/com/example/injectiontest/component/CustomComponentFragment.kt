package com.example.injectiontest.component

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import com.example.injectiontest.R
import com.example.injectiontest.util.viewScoped
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject
import javax.inject.Provider

@AndroidEntryPoint
class CustomComponentFragment : Fragment(R.layout.fragment_customcomponent){

    @Inject
    internal lateinit var presenterProvider: Provider<CustomComponentPresenter>

    private val presenter by viewScoped { presenterProvider.get() }

    @Inject
    lateinit var myCustomComponentHolderFactory: MyComponentHolder.Factory

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        myCustomComponentHolderFactory.create("Gabriel")
    }

    companion object {

        fun newInstance() = CustomComponentFragment()
    }
}
