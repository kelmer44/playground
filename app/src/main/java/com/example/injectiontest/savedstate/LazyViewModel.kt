package com.example.injectiontest.savedstate

import androidx.lifecycle.ViewModel
import kotlin.properties.ReadOnlyProperty
import kotlin.reflect.KProperty

class LazyViewModel<T:ViewModel>(): ReadOnlyProperty<Any, T> {
    override fun getValue(thisRef: Any, property: KProperty<*>): T {
        TODO("Not yet implemented")
    }
}
