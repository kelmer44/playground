package com.example.app_processor

import javax.lang.model.element.VariableElement
import javax.lang.model.type.TypeMirror

/**
 * Represents all reflective data on the annotated ViewModels required to
 * generate the associated Dagger Module
 * @property scope Scope it is assigned to
 * @property packageName package where the Module would reside
 * @property viewModelName Name of the ViewModel class
 * @property viewModelType semantic type of the ViewModel
 * @property constructorArguments map of constructor arguments for this ViewModel in the form [argName, argType]
 * @property optInSavedStateViewModel will use a SavedStateViewModelFactory to provide the ViewModels.
 */
data class ComponentData(
    val scope: ScopeSet,
    val packageName: String,
    val viewModelName: String,
    val viewModelType: TypeMirror,
    val constructorArguments: Map<String, VariableElement>,
    val optInSavedStateViewModel: Boolean,
    val parentClass: TypeMirror
)
