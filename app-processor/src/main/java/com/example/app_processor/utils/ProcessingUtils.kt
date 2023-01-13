package com.bamtechmedia.dominguez.utils

import javax.lang.model.element.Element
import javax.lang.model.type.MirroredTypeException
import javax.lang.model.type.TypeMirror
import kotlin.reflect.KClass

/**
 * Retrieves the TypeMirror from the annotation value when the type is a class. During processing we do not have
 * the classes on the classloader so we just need to fail and catch the typemirror generated on the exception.
 */
@Suppress("TooGenericExceptionThrown")
inline fun <reified T : Annotation> Element.getAnnotationClassValue(f: T.() -> KClass<*>): TypeMirror = try {
    getAnnotation(T::class.java).f()
    throw Exception("Expected to get a MirroredTypeException")
} catch (e: MirroredTypeException) {
    e.typeMirror
}
