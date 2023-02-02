package com.example.injectiontest.component.view

import android.content.Context
import android.util.AttributeSet
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LifecycleRegistry
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelStore
import androidx.lifecycle.ViewModelStoreOwner
import androidx.lifecycle.ViewTreeLifecycleOwner
import androidx.lifecycle.ViewTreeViewModelStoreOwner
import androidx.lifecycle.get
import com.example.injectiontest.component.experience.PlaybackExperience
import com.example.injectiontest.component.component.PlayerComponentHolder
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class PlaybackExperienceView : ConstraintLayout, ViewModelStoreOwner, LifecycleOwner {

    @Inject
    lateinit var presenter: Presenter

    private val registry = LifecycleRegistry(this)
    private var parentViewModelStoreOwner: ViewModelStoreOwner? = null

    constructor(context: Context) : this(context, null)

    constructor(context: Context, attrs: AttributeSet?) : this(context, attrs, 0)

    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs, defStyleAttr) {
        ViewTreeLifecycleOwner.set(this, this)
        ViewTreeViewModelStoreOwner.set(this, this)
    }

    override fun getLifecycle(): Lifecycle = registry

    override fun getViewModelStore(): ViewModelStore = viewModelStoreOwner().viewModelStore

    private fun viewModelStoreOwner(): ViewModelStoreOwner =
        ViewModelProvider(checkNotNull(parentViewModelStoreOwner))
            .get<ViewModelStoreHolder>().viewsModelStoreOwner

    internal class ViewModelStoreHolder : ViewModel() {
        val viewsModelStoreOwner = PlayerViewModelStoreOwner()
    }

    internal class PlayerViewModelStoreOwner : ViewModelStoreOwner {
        val store = ViewModelStore()
        override fun getViewModelStore(): ViewModelStore = store
    }

    fun setup(
        viewModelStoreOwner: ViewModelStoreOwner,
        lifecycleOwner: LifecycleOwner,
        playbackExperience: PlaybackExperience
    ) {
        if(this.parentViewModelStoreOwner == null) {
            this.parentViewModelStoreOwner = viewModelStoreOwner
            lifecycleOwner.lifecycle.addObserver(
                object : LifecycleEventObserver {
                    override fun onStateChanged(source: LifecycleOwner, event: Lifecycle.Event) {
                        registry.handleLifecycleEvent(event)
                    }
                }
            )
            presenter.setup(
                viewModelStoreOwner = this,
                lifecycleOwner = this,
                playbackExperience = playbackExperience
            )
        } else {
            check(viewModelStoreOwner == this.parentViewModelStoreOwner) {
                "Calling setup twice with different viewModelStoreOwner values is a mistake."
            }
        }
    }

    fun tearDown() {
        registry.handleLifecycleEvent(Lifecycle.Event.ON_DESTROY)
        viewModelStoreOwner().viewModelStore.clear()
        parentViewModelStoreOwner = null
    }

    interface Presenter {

        val playerComponentHolder: PlayerComponentHolder

        fun setup(
            viewModelStoreOwner: ViewModelStoreOwner,
            lifecycleOwner: LifecycleOwner,
            playbackExperience: PlaybackExperience
        )
    }
}
