/*
 * Extracted from
 * https://androidx.tech/artifacts/room/room-compiler/2.3.0-alpha02-source/androidx/room/ext/processing_env_ext.kt.html
 */
@file:Suppress("TooManyFunctions")
package com.bamtechmedia.dominguez.utils

import com.squareup.javapoet.TypeName
import javax.annotation.processing.ProcessingEnvironment
import javax.lang.model.element.TypeElement
import javax.lang.model.type.TypeKind
import javax.lang.model.type.TypeMirror
import kotlin.reflect.KClass

/**
 * Query a type element by KClass and return null if it does not exist
 */
fun ProcessingEnvironment.findTypeElement(
    klass: KClass<*>
): TypeElement? = findTypeElement(klass.java.canonicalName!!)

/**
 * Query a type element by TypeName and return null if it does not exist
 */
fun ProcessingEnvironment.findTypeElement(
    typeName: TypeName
): TypeElement? = findTypeElement(typeName.toString())

/**
 * Query a type element by qualified name and return null if it does not exist
 */
fun ProcessingEnvironment.findTypeElement(
    qName: String
): TypeElement? = elementUtils.getTypeElement(qName)

/**
 * Query a type element by KClass and throw if it does not exist
 */
fun ProcessingEnvironment.requireTypeElement(
    klass: KClass<*>
): TypeElement = requireTypeElement(klass.java.canonicalName!!)

/**
 * Query a type element by TypeName and throw if it does not exist
 */
fun ProcessingEnvironment.requireTypeElement(
    typeName: TypeName
): TypeElement = requireTypeElement(typeName.toString())

/**
 * Query a type element by qualified name and throw if it does not exist
 */
fun ProcessingEnvironment.requireTypeElement(
    qName: String
): TypeElement = checkNotNull(elementUtils.getTypeElement(qName)) {
    // we do not throw MissingTypeException here as this should be called only if the type should
    // be there
    "Couldn't find required type $qName"
}

/**
 * Query a TypeMirror by KClass and throw if it does not exist
 */
fun ProcessingEnvironment.requireTypeMirror(
    klass: KClass<*>
): TypeMirror = requireTypeMirror(klass.java.canonicalName!!)

/**
 * Query a TypeMirror by TypeName and throw if it does not exist
 */
fun ProcessingEnvironment.requireTypeMirror(
    typeName: TypeName
): TypeMirror = requireTypeMirror(typeName.toString())

/**
 * Query a TypeMirror by qualified name and throw if it does not exist
 */
fun ProcessingEnvironment.requireTypeMirror(
    qName: String
): TypeMirror = checkNotNull(findTypeMirror(qName)) {
    "couldn't find required type mirror $qName"
}

/**
 * Query a type mirror by KClass and return null if it does not exist
 */
fun ProcessingEnvironment.findTypeMirror(
    klass: KClass<*>
): TypeMirror? = findTypeMirror(klass.java.canonicalName!!)

/**
 * Query a type mirror by TypeName and return null if it does not exist
 */
fun ProcessingEnvironment.findTypeMirror(
    typeName: TypeName
): TypeMirror? = findTypeMirror(typeName.toString())

private val PRIMITIVE_TYPE_MAPPING = TypeKind.values().filter {
    it.isPrimitive
}.associateBy {
    it.name.lowercase()
}

/**
 * Query a type mirror by qualified name and return null if it does not exist
 */
fun ProcessingEnvironment.findTypeMirror(
    qName: String
): TypeMirror? {
    // first check primitives. Even though it is less likely, it is fast to check and can avoid a
    // call to the processor
    PRIMITIVE_TYPE_MAPPING[qName]?.let {
        return typeUtils.getPrimitiveType(it)
    }
    return findTypeElement(qName)?.asType()
}
