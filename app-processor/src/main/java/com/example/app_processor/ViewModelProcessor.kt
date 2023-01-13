package com.example.app_processor

import com.bamtechmedia.dominguez.utils.getAnnotationClassValue
import com.example.app_annotations.ActivityViewModel
import com.example.app_annotations.FragmentViewModel
import com.example.app_annotations.ViewModelConstructor
import com.squareup.javapoet.ClassName
import com.squareup.javapoet.JavaFile
import javax.annotation.processing.AbstractProcessor
import javax.annotation.processing.Filer
import javax.annotation.processing.ProcessingEnvironment
import javax.annotation.processing.RoundEnvironment
import javax.annotation.processing.SupportedSourceVersion
import javax.lang.model.SourceVersion
import javax.lang.model.element.Element
import javax.lang.model.element.ElementKind
import javax.lang.model.element.ExecutableElement
import javax.lang.model.element.TypeElement
import javax.lang.model.type.TypeMirror

/**
 * This processor gathers all androidx.lifecycle.ViewModel subclasses annotated with either [ActivityViewModel] or
 * [FragmentViewModel] to generate a matching Dagger module that provides an instance of such ViewModel. This makes such
 * ViewModel injectable in [ActivityComponent] and [FragmentComponent], respectively.
 *
 * If the ViewModel implements an interface or extends an abstract class and the binding for such interface/class is
 * needed instead, it can be specified by using the [parentClass] attribute of the annotations.
 *
 * @see [FragmentViewModel.parentClass] and [ActivityViewModel.parentClass]
 *
 * The underlying processor reads the appropriate constructor from the ViewModel (either single constructor or the one
 * marked with [ViewModelConstructor]) and produces a @Provides method injecting all parameters declared in such
 * constructor.
 *
 * Classes annotated with [ActivityViewModel] or [FragmentViewModel] must extend androidx.lifecycle.ViewModel and have
 * either a single constructor or a constructor annotated with [ViewModelConstructor].
 *
 * Qualifiers and additional annotations are respected in the process.
 *
 * Special cases are:
 *
 * * SavedStateHandler, which if present in the ViewModel constructor, is automatically wired in by means
 * of a [androidx.lifecycle.AbstractSavedStateViewModelFactory].
 * (see [ViewModelUtils](/core-utils/src/main/java/com/bamtechmedia/dominguez/core/utils/ViewModelUtils.java))
 *
 * * BundleArguments, that is, extras on the intent of Activities or arguments on Fragments. If a parameter on the
 * constructor is annotated with [BundleArgument], with a given argument name, it will be extracted from the
 * extras/fragment arguments and injected automatically. Note that string arguments default to empty string, and
 * Parcelables will default to null.
 *
 * @see [BundleArgument]
 *
 * The annotation processor is marked as isolating and therefore supports incremental building, meaning it will only
 * process annotations if the originating element has been recompiled. This can be checked against by inspecting the
 * Performance section on Gradle Build reports. If we remove the isolating mark from the
 * [incremental.annotation.processing](/coreProcessor/src/main/resources/META-INF/gradle/incremental.annotation.processors)
 * file under META-INF, compiling the app with no code changes will still retrigger the `kaptDebugKotlin` tasks on each
 * build (A good spot to test this is the sample app).
 *
 * @see https://docs.gradle.org/current/userguide/java_plugin.html#sec:incremental_annotation_processing for info on
 * incremental processors
 *
 * @see https://github.bamtech.co/pages/Android/dmgz-docs/administrative/rfc/viewmodel_module_codegen/ for RFC on this
 * feature.
 */
@SupportedSourceVersion(SourceVersion.RELEASE_8)
class ViewModelProcessor : AbstractProcessor() {

    lateinit var filer: Filer

    private val viewModel = ClassName.get("androidx.lifecycle", "ViewModel")

    override fun init(processingEnv: ProcessingEnvironment) {
        super.init(processingEnv)
        filer = processingEnv.filer
    }

    override fun getSupportedAnnotationTypes(): MutableSet<String> = mutableSetOf(
        ActivityViewModel::class.java.canonicalName, FragmentViewModel::class.java.canonicalName
    )

    override fun process(annotations: MutableSet<out TypeElement>?, roundEnv: RoundEnvironment): Boolean {
        roundEnv.getElementsAnnotatedWithAny(setOf(ActivityViewModel::class.java, FragmentViewModel::class.java))
            .associateWith { getComponentData(it) }.filterNotNullValues().onEach { (element, component) ->
                val moduleName = component.viewModelName + "_" + component.scope.javaClass.simpleName + "Module"
                val build: JavaFile = JavaFile.builder(
                    component.packageName, ViewModelModuleBuilder(processingEnv, element, moduleName, component).build()
                ).build()
                build.writeTo(filer)
            }

        return true
    }

    private fun <K, V> Map<K, V?>.filterNotNullValues(): Map<K, V> =
        buildMap { for ((k, v) in this@filterNotNullValues) if (v != null) put(k, v) }

    private fun getComponentData(element: Element): ComponentData {
        val packageName = processingEnv.elementUtils.getPackageOf(element).toString()
        val enclosedElements = element.enclosedElements
        val asTypeElement = (element as TypeElement)
        val isViewModel = processingEnv.typeUtils.isAssignable(
            asTypeElement.asType(), processingEnv.elementUtils.getTypeElement(viewModel.canonicalName()).asType()
        )
        if (!isViewModel) {
            error("Classes annotated with ActivityViewModel or FragmentViewModel must extend ViewModel")
        }

        val viewModelName = element.simpleName.toString()
        val viewModelType = element.asType()

        val constructor = getConstructor(enclosedElements)

        val scope = getScope(element)

        val optInSavedStateViewModel = if (scope == ScopeSet.Activity)
            element.getAnnotation(ActivityViewModel::class.java).optInSavedSateViewModel
        else
            element.getAnnotation(FragmentViewModel::class.java).optInSavedSateViewModel

        val parentClass: TypeMirror = if (scope == ScopeSet.Activity)
            element.getAnnotationClassValue<ActivityViewModel> { parentClass }
        else
            element.getAnnotationClassValue<FragmentViewModel> { parentClass }

        checkParentClass(element, parentClass)

        return ComponentData(
            scope,
            packageName,
            viewModelName,
            viewModelType,
            constructor.parameters.associateBy { it.simpleName.toString() },
            optInSavedStateViewModel,
            parentClass
        )
    }

    private fun checkParentClass(element: TypeElement, parentClass: TypeMirror) {
        if (parentClass.toString() == PARENT_CLASS_DEFAULT) return
        val parentClassAsElement = processingEnv.typeUtils.asElement(parentClass)
        if (!processingEnv.typeUtils.isAssignable(element.asType(), parentClassAsElement.asType()))
            error("ViewModel does not extend the class specified as ParentClass")
    }

    private fun getScope(element: TypeElement): ScopeSet {
        val isActivityScoped = element.getAnnotation(ActivityViewModel::class.java) != null
        val isFragmentScoped = element.getAnnotation(FragmentViewModel::class.java) != null
        if (isActivityScoped && isFragmentScoped) {
            error("ViewModel must to be either Fragment or Activity Scoped")
        }
        return if (isActivityScoped) ScopeSet.Activity else ScopeSet.Fragment
    }

    private fun getConstructor(enclosedElements: List<Element>): ExecutableElement {
        val constructors =
            enclosedElements.filter { it.kind == ElementKind.CONSTRUCTOR }.map { it as ExecutableElement }

        return if (constructors.size == 1) {
            constructors.first()
        } else {
            val filteredConstructors = constructors.filter {
                it.getAnnotation(ViewModelConstructor::class.java) != null
            }
            if (filteredConstructors.size != 1) {
                error(
                    "Either one constructor or only one constructor annotated with @ViewModelConstructor is needed " +
                        "for @ActivityViewModel or @FragmentViewModel annotated ViewModels"
                )
            } else
                filteredConstructors.first()
        }
    }

    companion object {
        const val PARENT_CLASS_DEFAULT = "com.bamtechmedia.dominguez.annotation.DefaultParentClass"
    }
}
