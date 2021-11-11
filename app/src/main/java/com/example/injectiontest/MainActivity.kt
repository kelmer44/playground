package com.example.injectiontest

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.injectiontest.flow.FlowFragment
import com.example.injectiontest.lobby.LobbyFragment
import com.example.injectiontest.model.ParamHolder
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
//        lobbyFragment()
        flowFragment()
    }

    private fun flowFragment() {
        supportFragmentManager.beginTransaction()
            .add(R.id.content_fragment, FlowFragment.newInstance())
            .commit()
    }

    private fun lobbyFragment() {
        supportFragmentManager.beginTransaction()
            .add(R.id.content_fragment, LobbyFragment.newInstance("This is my passed Argument", ParamHolder("This is my parcelable!")))
            .commit()
    }
}
