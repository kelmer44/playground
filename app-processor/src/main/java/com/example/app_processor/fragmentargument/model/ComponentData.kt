package com.example.app_processor.fragmentargument.model

import javax.lang.model.type.TypeMirror

data class ComponentData(
    val packageName: String, // Needed to figure out where to put our Module
    val fieldName: String, // Not really needed
    val argumentName: String, // Whatever we put in @Named we use as paramname
    val argumentType: TypeMirror // We need to know the type of the injected field so we can extract from the fragment
)
