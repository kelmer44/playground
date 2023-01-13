package com.example.injectiontest.util

import android.view.View
import androidx.fragment.app.Fragment
import androidx.lifecycle.DefaultLifecycleObserver
import androidx.lifecycle.LifecycleOwner
import kotlin.properties.ReadOnlyProperty
import kotlin.reflect.KProperty

/**
 * [ReadOnlyProperty] for creating and de-referencing an instance of type [T] reference in the lifetime of a
 * [Fragment]'s View.
 *
 * This is useful for creating presenters that need to be scoped to a single instance in the lifetime of the View.
 */
class ViewScopedInstanceProperty<T : Any>(
    private val fragment: Fragment,
    private val factory: (View) -> T,
    private val onPreDestroy: ((T?) -> Unit)? = null
) : ReadOnlyProperty<Any, T?> {

    private var viewScopedInstance: T? = null

    private val viewLifecycleOwnerObserver = object : DefaultLifecycleObserver {

        override fun onCreate(owner: LifecycleOwner) {
            if (viewScopedInstance == null) {
                viewScopedInstance = factory.invoke(fragment.requireView())
            }
        }

        override fun onDestroy(owner: LifecycleOwner) {
            onPreDestroy?.invoke(viewScopedInstance)
            viewScopedInstance = null
        }
    }

    init {
        fragment.lifecycle.addObserver(object : DefaultLifecycleObserver {
            override fun onCreate(owner: LifecycleOwner) {
                fragment.viewLifecycleOwnerLiveData.observe(fragment) { viewLifecycleOwner ->
                    viewLifecycleOwner?.lifecycle?.addObserver(viewLifecycleOwnerObserver)
                }
            }
        })
    }

    override fun getValue(thisRef: Any, property: KProperty<*>): T {
        assureViewInCreatedState(property)
        return viewScopedInstance ?: factory.invoke(fragment.requireView()).also {
            viewScopedInstance = it
        }
    }

    private fun assureViewInCreatedState(property: KProperty<*>) {
        if (fragment.view == null) {
            throw IllegalStateException(
                """
            Property (${property.name}) was accessed when Fragment's ($fragment) view is null.
            Property was accessed when:
                - view was not created yet
                - view was already destroyed
                - this Fragment does not have a view
                """.trimIndent()
            )
        }
    }
}

/**
 * Convenience method to create a reference in a fragment that is created with the [factory] in [Fragment.onViewCreated]
 * and referenced in [Fragment.onDestroyView].
 */
fun <T : Any> Fragment.viewScoped(
    onPreDestroy: (T?) -> Unit = {},
    factory: ((View) -> T),
) = ViewScopedInstanceProperty(this, factory, onPreDestroy)
