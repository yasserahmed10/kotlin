// RUN_PIPELINE_TILL: FRONTEND
// FIR_IDENTICAL
// CHECK_TYPE

data class A(val foo: Int)

operator fun A.<!EXTENSION_SHADOWED_BY_MEMBER!>component1<!>(): String = ""

fun test(a: A) {
    val (b) = a
    b checkType { _<Int>() }
}

/* GENERATED_FIR_TAGS: classDeclaration, data, destructuringDeclaration, funWithExtensionReceiver, functionDeclaration,
functionalType, infix, lambdaLiteral, localProperty, nullableType, operator, primaryConstructor, propertyDeclaration,
stringLiteral, typeParameter, typeWithExtension */
