package com.example.injectiontest

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.Fragment
import com.example.injectiontest.archselector.ArchSelectorFragment
import com.example.injectiontest.archselector.arch.ArchFragment
import com.example.injectiontest.archselector.archalt.ArchAltFragment
import com.example.injectiontest.component.PlaybackFragment
import com.example.injectiontest.flow.FlowFragment
import com.example.injectiontest.flow.lobby.LobbyFragment
import com.example.injectiontest.hiltviewmodel.HVMFragment
import com.example.injectiontest.keystore.KeystoreFragment
import com.example.injectiontest.model.ParamHolder
import com.example.injectiontest.savedstate.SavedStateFragment
import dagger.hilt.android.AndroidEntryPoint
import timber.log.Timber
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    @Inject
    lateinit var viewModel: MainActivityViewModel

    @Inject
    lateinit var watcher: MemoryWatcher

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
//        archSelectorFragment()
//        archFragment()
//        archAltFragment()
//        savedStateFragment()
//        hiltFragment()
//        lobbyFragment()
//        flowFragment()
        watcher.addReference(this)
        if(supportFragmentManager.findFragmentByTag("MYTAG") == null) {
            Timber.w("Player Setup = Transacting!")
//            customComponent()
            keystoreFragment()
        }
        else {
            Timber.w("Player Setup = Skipping transaction!")
        }
    }

    private fun keystoreFragment() {
        transact(KeystoreFragment.newInstance())
    }

    private fun hiltFragment() {
        transact(HVMFragment())
    }

    fun transact(fragment: Fragment){
        supportFragmentManager.beginTransaction()
            .replace(R.id.content_fragment, fragment, "MYTAG")
            .commit()
    }

    private fun customComponent() {
        transact(PlaybackFragment.newInstance())
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
