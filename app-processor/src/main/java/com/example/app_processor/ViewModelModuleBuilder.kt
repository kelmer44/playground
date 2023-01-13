package com.example.app_processor

import com.example.app_processor.ViewModelProcessor.Companion.PARENT_CLASS_DEFAULT
import com.bamtechmedia.dominguez.utils.requireTypeMirror
import com.example.app_annotations.BundleArgument
import com.squareup.javapoet.AnnotationSpec
import com.squareup.javapoet.ClassName
import com.squareup.javapoet.CodeBlock
import com.squareup.javapoet.MethodSpec
import com.squareup.javapoet.ParameterSpec
import com.squareup.javapoet.TypeName
import com.squareup.javapoet.TypeSpec
import javax.annotation.processing.ProcessingEnvironment
import javax.lang.model.element.Element
import javax.lang.model.element.Modifier
import javax.lang.model.element.VariableElement
import javax.lang.model.type.TypeMirror

/**
 * Uses JavaPoet to generate a Dagger module with the given scope that provides
 * an instance of the annotated ViewModel.
 */
class ViewModelModuleBuilder(
    private val processingEnv: ProcessingEnvironment,
    private val element: Element,
    private val moduleName: String,
    private val component: ComponentData,
) {

    private val savedStateHandleParam = "savedStateHandle"

    private val moduleAnnotation = ClassName.get("dagger", "Module")
    private val installInAnnotation = ClassName.get("dagger.hilt", "InstallIn")
    private val providesAnnotation = ClassName.get("dagger", "Provides")
    private val viewModelUtils = ClassName.get("com.example.injectiontest.util", "ViewModelUtils")
    private val savedStateHandle = ClassName.get("androidx.lifecycle", "SavedStateHandle")

    /**
     * Generates the Module class.
     */
    fun build(): TypeSpec = TypeSpec.classBuilder(moduleName)
        //Passing in the originating element ensures isolating annotation processing works
        .addOriginatingElement(element)
        .addModifiers(Modifier.ABSTRACT)
        //Module annotation
        .addAnnotation(AnnotationSpec.builder(moduleAnnotation).build())
        //InstallIn(componentType)
        .addAnnotation(
            AnnotationSpec.builder(installInAnnotation)
                .addMember(
                    "value", CodeBlock.of(
                        "\$T.class",
                        component.scope.componentType
                    )
                )
                .build()
        )
        //Provides method
        .addMethod(
            MethodSpec
                .methodBuilder("provide" + component.viewModelName)
                .addModifiers(Modifier.STATIC)
                .addAnnotation(AnnotationSpec.builder(providesAnnotation).build())
                .addParameter(
                    ParameterSpec.builder(
                        component.scope.injectableClass,
                        component.scope.paramName
                    ).build()
                )
                .addParameters(buildParameterList(component.constructorArguments))
                .returns(getReturnType())
                .addStatement(buildReturnStatement(component))
                .build()
        )
        .build()

    private fun getReturnType(): TypeName =
        if (component.parentClass.toString() == PARENT_CLASS_DEFAULT)
            TypeName.get(component.viewModelType)
        else TypeName.get(component.parentClass as TypeMirror)

    private fun buildReturnStatement(component: ComponentData): CodeBlock {
        val argumentList = buildArgumentList(component.constructorArguments).joinToString(",\n")
        return if (component.optInSavedStateViewModel) {
            buildWithStateHandle(argumentList, component)
        } else {
            buildWithoutStateHandle(argumentList, component)
        }
    }

    private fun buildWithoutStateHandle(argumentList: String, component: ComponentData): CodeBlock {
        return CodeBlock.of(
            "return \$T.getViewModel(\n" +
                component.scope.paramName + ",\n " +
                TypeName.get(component.viewModelType) + ".class,\n" +
                " () -> new " + TypeName.get(component.viewModelType) +
                "(" + argumentList + ")\n" +
                ")",
            viewModelUtils
        )
    }

    private fun buildWithStateHandle(argumentList: String, component: ComponentData): CodeBlock {
        return CodeBlock.of(
            "return \$T.getSavedStateViewModel(\n" +
                component.scope.paramName + ",\n " +
                TypeName.get(component.viewModelType) + ".class,\n" +
                getDefaultArgs(component) + ",\n" +
                " ($savedStateHandleParam) -> new " + TypeName.get(component.viewModelType) +
                "(" + argumentList + ")\n" +
                ")",
            viewModelUtils
        )
    }

    private fun getDefaultArgs(component: ComponentData) =
        when (component.scope) {
            is ScopeSet.Fragment -> "${component.scope.paramName}.getArguments()"
            is ScopeSet.Activity -> "${component.scope.paramName}.getIntent().getExtras()"
        }

    private fun buildArgumentList(constructorArguments: Map<String, VariableElement>): List<String> {
        return constructorArguments.map {
            if (isParamSavedStateHandle(it.value.asType())) {
                if (component.optInSavedStateViewModel)
                    savedStateHandleParam
                else
                    throw java.lang.IllegalStateException(
                        "You must opt in to SavedStateHandleViewModel providing by setting " +
                            "optInSavedSateViewModel = true on your annotation in order to inject SavedStateHandle"
                    )
            } else if (isArgument(it.value)) {
                val keyName = it.value.getAnnotation(BundleArgument::class.java).name
                extractArgument(keyName, it.value)
            } else {
                it.key
            }
        }
    }

    private val parcelableClass: ClassName = ClassName.get("android.os", "Parcelable")
    private val parcelableTypeMirror = processingEnv.requireTypeMirror(parcelableClass)

    private fun extractArgument(keyName: String, element: VariableElement): String {
        val getArguments = getDefaultArgs(component)
        val type = element.asType()

        //For parcelable we must cast to the real type
        if (processingEnv.typeUtils.isAssignable(type, parcelableTypeMirror)) {
            val typeName = type.toString()
            return "($typeName)$getArguments.getParcelable(\"$keyName\")"
        }

        //Primitive types can be boxed or unboxed
        for (key in typeMapping.keys) {
            if (processingEnv.typeUtils.isAssignable(type, key)) {
                return getArguments + ".${typeMapping[key]}".format(keyName)
            }
        }

        throw java.lang.IllegalArgumentException("Cannot process argument with key $keyName")
    }

    private val typeMapping = mapOf<TypeMirror, String>(
        processingEnv.elementUtils.getTypeElement("java.lang.Boolean").asType() to "getBoolean(\"%s\")",
        processingEnv.elementUtils.getTypeElement("java.lang.Integer").asType() to "getInt(\"%s\")",
        processingEnv.elementUtils.getTypeElement("java.lang.Float").asType() to "getFloat(\"%s\")",
        processingEnv.elementUtils.getTypeElement("java.lang.Double").asType() to "getDouble(\"%s\")",
        processingEnv.elementUtils.getTypeElement("java.lang.String").asType() to "getString(\"%s\")",
    )

    private fun isArgument(type: VariableElement) = type.getAnnotation(BundleArgument::class.java) != null

    /**
     * Builds parameter list for provide function.
     *
     * Will include everything that is injectable through dagger, and skip the rest, e.g. the savedStateHandle and the
     * arguments (which are extracted from the activity/fragment).
     */
    private fun buildParameterList(constructorArguments: Map<String, VariableElement>): List<ParameterSpec> {
        return constructorArguments.mapNotNull {
            if (isParamSavedStateHandle(it.value.asType()) || isArgument(it.value))
                null
            else
                ParameterSpec
                    .builder(
                        TypeName.get(it.value.asType()),
                        it.key
                    )
                    .addAnnotations(buildParameterAnnotations(it.value))
                    .build()
        }
    }

    private fun isParamSavedStateHandle(type: TypeMirror) = TypeName.get(type) == savedStateHandle

    /**
     * Adds all original annotations
     */
    private fun buildParameterAnnotations(type: VariableElement): List<AnnotationSpec> {
        val annotationMirrors = type.annotationMirrors
        return annotationMirrors.map {
            AnnotationSpec.get(it)
        }
    }
}
