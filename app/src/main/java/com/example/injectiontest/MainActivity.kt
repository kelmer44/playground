package com.example.injectiontest

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.Fragment
import com.example.injectiontest.archselector.ArchSelectorFragment
import com.example.injectiontest.archselector.arch.ArchFragment
import com.example.injectiontest.archselector.archalt.ArchAltFragment
import com.example.injectiontest.flow.FlowFragment
import com.example.injectiontest.flow.lobby.LobbyFragment
import com.example.injectiontest.model.ParamHolder
import com.example.injectiontest.savedstate.SavedStateFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
//        archSelectorFragment()
//        archFragment()
        archAltFragment()
        savedStateFragment()
//        lobbyFragment()
//        flowFragment()
    }

    fun transact(fragment: Fragment){
        supportFragmentManager.beginTransaction()
            .add(R.id.content_fragment, fragment)
            .commit()
    }

    private fun savedStateFragment() {
        transact(SavedStateFragment())
    }

    private fun archSelectorFragment() {
        transact(ArchSelectorFragment.newInstance())
    }

    private fun archAltFragment() {
        transact(ArchAltFragment.newInstance())
    }

    private fun archFragment() {
        transact(ArchFragment.newInstance())
    }

    private fun flowFragment() {
        transact(FlowFragment.newInstance())
    }

    private fun lobbyFragment() {
      transact(LobbyFragment.newInstance("This is my passed Argument", ParamHolder("This is my parcelable!")))
    }
}
