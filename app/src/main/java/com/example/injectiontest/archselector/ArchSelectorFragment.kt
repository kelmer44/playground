package com.example.injectiontest.archselector

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.injectiontest.R
import com.example.injectiontest.archselector.arch.ArchFragment
import com.example.injectiontest.archselector.archalt.ArchAltFragment
import com.example.injectiontest.databinding.FragmentArchselectorBinding

class ArchSelectorFragment : Fragment(R.layout.fragment_archselector) {

    lateinit var binding: FragmentArchselectorBinding

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = FragmentArchselectorBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setup()
    }

    private fun setup() {
        binding.archAlt.setOnClickListener {
            parentFragmentManager.beginTransaction()
                .replace(R.id.content_fragment, ArchAltFragment.newInstance())
                .addToBackStack("arch")
                .commit()
        }
        binding.archNormal.setOnClickListener {
            parentFragmentManager.beginTransaction()
                .replace(R.id.content_fragment, ArchFragment.newInstance())
                .addToBackStack("archAlt")
                .commit()
        }
    }

    companion object {
        fun newInstance() = ArchSelectorFragment()
    }
}
