package com.example.app_processor.fragmentargument.codegen

import com.example.app_processor.fragmentargument.model.ComponentData
import com.squareup.javapoet.AnnotationSpec
import com.squareup.javapoet.ClassName
import com.squareup.javapoet.CodeBlock
import com.squareup.javapoet.MethodSpec
import com.squareup.javapoet.ParameterSpec
import com.squareup.javapoet.TypeName
import com.squareup.javapoet.TypeSpec
import com.squareup.kotlinpoet.asTypeName
import java.lang.IllegalStateException
import javax.annotation.processing.ProcessingEnvironment
import javax.inject.Named
import javax.lang.model.element.Modifier
import javax.lang.model.type.TypeMirror

class FragmentArgumentBuilder(
    private val moduleName: String,
    private val component: ComponentData,
    private val processingEnv: ProcessingEnvironment
) {
    private val fragmentClassName = ClassName.get("androidx.fragment.app", "Fragment")
    private val installInAnnotation = ClassName.get("dagger.hilt", "InstallIn")
    private val moduleAnnotation = ClassName.get("dagger", "Module")
    private val providesAnnotation = ClassName.get("dagger", "Provides")
    private val fragmentComponentType = ClassName.get("dagger.hilt.android.components", "FragmentComponent")


    private val type: ArgumentTypes = determineType(component)

    private fun determineType(component: ComponentData): ArgumentTypes {
        val argumentType = component.argumentType

        return when {
            processingEnv.typeUtils.isAssignable(argumentType, getBasicType("java.lang.String")) -> ArgumentTypes.STRING
            processingEnv.typeUtils.isAssignable(argumentType, getBasicType("java.lang.Byte")) -> ArgumentTypes.BYTE
            processingEnv.typeUtils.isAssignable(argumentType, getBasicType("java.lang.Boolean")) -> ArgumentTypes.BOOLEAN
            processingEnv.typeUtils.isAssignable(argumentType, getBasicType("java.lang.Integer")) -> ArgumentTypes.INTEGER
            processingEnv.typeUtils.isAssignable(argumentType, getBasicType("java.lang.Float")) -> ArgumentTypes.FLOAT
            processingEnv.typeUtils.isAssignable(argumentType, getBasicType("java.lang.Double")) -> ArgumentTypes.DOUBLE
            processingEnv.typeUtils.isAssignable(argumentType, getBasicType("android.os.Parcelable")) -> ArgumentTypes.PARCELABLE
            else -> ArgumentTypes.UNKNOWN
        }
    }

    private fun getBasicType(type: String): TypeMirror = processingEnv.elementUtils.getTypeElement(type).asType()

    fun build(): TypeSpec = TypeSpec.classBuilder(moduleName)
        .addModifiers(Modifier.ABSTRACT)
        .addAnnotation(AnnotationSpec.builder(moduleAnnotation).build())
        .addAnnotation(
            AnnotationSpec.builder(installInAnnotation)
                .addMember("value", CodeBlock.of("\$T.class", fragmentComponentType))
                .build()
        )
        .addMethod(
            MethodSpec
                .methodBuilder("provide" + component.argumentName.replaceFirstChar { it.uppercaseChar() })
                .addModifiers(Modifier.STATIC)
                .addAnnotation(AnnotationSpec.builder(providesAnnotation).build())
                .addAnnotation(
                    AnnotationSpec.builder(Named::class.java)
                        .addMember("value", "\$S", component.argumentName)
                        .build()
                )
                .addParameter(
                    ParameterSpec.builder(
                        fragmentClassName,
                        "fragment",
                    ).build()
                )
                .returns(TypeName.get(component.argumentType))
                .addStatement(buildReturnStatement(component))
                .build()
        )
        .build()

    private fun buildReturnStatement(component: ComponentData): String {
        if(type==ArgumentTypes.UNKNOWN) throw IllegalStateException("Incompatible type; cant extract type ${component.argumentType} from Fragment Arguments.")
        return returnSentence(type.functionCall, component)
    }

    private fun returnSentence(functionCall: String, component: ComponentData): String {
        return "return (${
            component.argumentType.asTypeName().toString()
        }) fragment.getArguments().$functionCall(\"${component.argumentName}\")"
    }

    enum class ArgumentTypes(val functionCall: String) {
        STRING("getString"),
        INTEGER("getInteger"),
        BYTE("getByte"),
        CHAR("getChar"),
        FLOAT("getFloat"),
        BOOLEAN("getBoolean"),
        DOUBLE("getDouble"),
        PARCELABLE("getParcelable"),
        UNKNOWN("none")
    }
}
