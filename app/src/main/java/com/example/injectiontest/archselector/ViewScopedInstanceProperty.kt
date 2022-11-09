package com.example.injectiontest.archselector.archalt

import android.view.View
import androidx.fragment.app.Fragment
import androidx.lifecycle.DefaultLifecycleObserver
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.Observer
import timber.log.Timber
import kotlin.properties.ReadOnlyProperty
import kotlin.reflect.KProperty

/**
 * [ReadOnlyProperty] for creating and de-referencing an instance of type [T] reference in the lifetime of a
 * [Fragment]'s View.
 *
 * This is useful for creating presenters that need to be scoped to a single instance in the lifetime of the View.
 */
class ViewScopedInstanceProperty<T : Any>(
    private val fragment: Fragment, private val factory: (View) -> T
) : ReadOnlyProperty<Any, T?> {

    private var viewScopedInstance: T? = null

    private val viewLifecycleOwnerObserver = object : DefaultLifecycleObserver {

        override fun onCreate(owner: LifecycleOwner) {
            viewScopedInstance = factory.invoke(fragment.requireView())
        }

        override fun onDestroy(owner: LifecycleOwner) {
            (viewScopedInstance as? OnViewDestroyCallback)?.onDestroyView()
            viewScopedInstance = null
        }
    }

    init {
        fragment.lifecycle.addObserver(object : DefaultLifecycleObserver {
            val viewLifecycleOwnerLiveDataObserver = Observer<LifecycleOwner?> {
                Timber.e("TROLOLO - LifecycleOwner is = $it")
                it?.lifecycle?.addObserver(viewLifecycleOwnerObserver)
            }
            override fun onCreate(owner: LifecycleOwner) {
                Timber.e("TROLOLO - Fragment created")
                fragment.viewLifecycleOwnerLiveData.observeForever(viewLifecycleOwnerLiveDataObserver)
            }

            override fun onDestroy(owner: LifecycleOwner) {
                Timber.e("TROLOLO - Fragment destroyed")
                fragment.viewLifecycleOwnerLiveData.removeObserver(viewLifecycleOwnerLiveDataObserver)
            }

        })
    }

    override fun getValue(thisRef: Any, property: KProperty<*>): T? = viewScopedInstance
}

/**
 * Optional interface that can be implemented by a [ViewScopedInstanceProperty] to receive a callback before the
 * instance is destroyed.
 */
interface OnViewDestroyCallback {
    /**
     * Invoked when the view scoped instance is destroyed.
     */
    fun onDestroyView()
}

/**
 * Convenience method to create a reference in a fragment that is created with the [factory] in [Fragment.onViewCreated]
 * and referenced in [Fragment.onDestroyView].
 */
fun <T : Any> Fragment.viewScoped(factory: (View) -> T): ReadOnlyProperty<Any, T?> =
    ViewScopedInstanceProperty(this, factory)
