// RUN_PIPELINE_TILL: FRONTEND
// FILE: 1.kt
package simpleObject

typealias SimpleObject = TestCase

object ExtendedInvokableObject

operator fun ExtendedInvokableObject.invoke() {}

object TestCase {
    val extendedPropertyLikeClbl = ExtendedInvokableObject
}

// FILE: 2.kt
import simpleObject.SimpleObject.extendedPropertyLikeClbl

/* GENERATED_FIR_TAGS: funWithExtensionReceiver, functionDeclaration, objectDeclaration, operator, propertyDeclaration,
typeAliasDeclaration */
