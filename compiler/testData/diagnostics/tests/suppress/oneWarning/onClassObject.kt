// RUN_PIPELINE_TILL: BACKEND
// FIR_IDENTICAL
class C {
    @Suppress("REDUNDANT_NULLABLE")
    companion object {
        val foo: String?? = null <!USELESS_CAST!>as Nothing??<!>
    }
}

/* GENERATED_FIR_TAGS: asExpression, classDeclaration, companionObject, nullableType, objectDeclaration,
propertyDeclaration, stringLiteral */
