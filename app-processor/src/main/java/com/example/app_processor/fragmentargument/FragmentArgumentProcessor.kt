package com.example.app_processor.fragmentargument

import com.example.app_annotations.FragmentArgument
import com.example.app_processor.fragmentargument.codegen.FragmentArgumentBuilder
import com.example.app_processor.fragmentargument.model.ComponentData
import com.squareup.javapoet.JavaFile
import java.io.File
import java.lang.IllegalStateException
import javax.annotation.processing.AbstractProcessor
import javax.annotation.processing.Filer
import javax.annotation.processing.ProcessingEnvironment
import javax.annotation.processing.RoundEnvironment
import javax.annotation.processing.SupportedSourceVersion
import javax.inject.Named
import javax.lang.model.SourceVersion
import javax.lang.model.element.Element
import javax.lang.model.element.TypeElement
import javax.tools.Diagnostic

@SupportedSourceVersion(SourceVersion.RELEASE_8)
class FragmentArgumentProcessor : AbstractProcessor() {

    override fun getSupportedAnnotationTypes(): MutableSet<String> =
        mutableSetOf(FragmentArgument::class.java.canonicalName)

    lateinit var filer: Filer
    override fun init(processingEnv: ProcessingEnvironment) {
        super.init(processingEnv)
        filer = processingEnv.filer
    }

    override fun process(
        annotations: MutableSet<out TypeElement>?,
        roundEnv: RoundEnvironment
    ): Boolean {

        processingEnv.messager.printMessage(Diagnostic.Kind.WARNING, "Processing! FragmentArgument")
        val kaptKotlinGeneratedDir =
            processingEnv.options[KAPT_KOTLIN_GENERATED_OPTION_NAME]
                ?: return false
        val components: List<ComponentData> = roundEnv.getElementsAnnotatedWith(FragmentArgument::class.java)
            .map {
                val componentData = getComponentData(it) ?: return false

                processingEnv.messager.printMessage(Diagnostic.Kind.WARNING, "Got componentData! $componentData")
                componentData
            }

        val groupBy: Map<String, List<ComponentData>> = components.groupBy { it.argumentName }


        groupBy.entries.forEach { pair ->
            val theseComponents = pair.value.distinctBy { it.argumentType }
            if(theseComponents.size != 1) throw IllegalStateException("Multiple types bound to item ${pair.key}!")
            val component = theseComponents.first()
            val moduleName =
                component.argumentName.toCamelCase().replaceFirstChar { it.uppercaseChar() } + "_" + "FragmentArgumentsModule"
            processingEnv.messager.printMessage(Diagnostic.Kind.WARNING, "Building file $moduleName")
            val build: JavaFile = JavaFile
                .builder(
                    component.packageName,
                    FragmentArgumentBuilder(moduleName, component, processingEnv).build()
                )
                .build()
            build
                .writeTo(filer)
        }
//        val componentMap = components.groupBy { it.packageName }


        return true
    }

    private fun String.toCamelCase() : String {
        val parts = this.split("_").toMutableList()
        for (i in parts.indices) {
            if (i > 0) {
                parts[i] = parts[i].replaceFirstChar { it.uppercaseChar() }
            }
        }
        return parts.joinToString("")
    }

    private fun getComponentData(element: Element): ComponentData? {
        val packageName = processingEnv.elementUtils.getPackageOf(element).toString()

        val fieldName = element.simpleName.toString()
        val enclosingElement = element.enclosingElement
        val namedAnnotation = element.getAnnotation(Named::class.java)
            ?: throw IllegalStateException("No @Named annotation found for $fieldName in $enclosingElement")
        val argumentName = namedAnnotation.value
        val type = element.asType()


        return ComponentData(
            packageName,
            fieldName,
            argumentName,
            type
        )
    }

    companion object {
        const val KAPT_KOTLIN_GENERATED_OPTION_NAME = "kapt.kotlin.generated"
    }

}
