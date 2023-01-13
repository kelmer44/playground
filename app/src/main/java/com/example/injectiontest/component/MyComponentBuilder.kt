package com.example.injectiontest.component

import dagger.BindsInstance
import dagger.hilt.DefineComponent
import javax.inject.Named

@DefineComponent.Builder
interface MyComponentBuilder {

    fun feedName(@BindsInstance @Named("custom_name") name: String) : MyComponentBuilder

    fun build(): MyComponent
}
