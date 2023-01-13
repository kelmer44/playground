package com.example.injectiontest.component

import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject
import dagger.hilt.EntryPoints
import javax.inject.Inject

class MyComponentHolder @AssistedInject constructor(
    @Assisted private val name: String,
    componentBuilder: MyComponentBuilder
) {

    private val component : MyComponent
        = componentBuilder
        .feedName(name)
        .build()

    private val entryPoint : MyStupidClass = EntryPoints.get(component, MyComponentEntryPoint::class.java)
        .getMyClass()

    @AssistedFactory
    interface Factory {

        fun create(name: String) : MyComponentHolder
    }
}
