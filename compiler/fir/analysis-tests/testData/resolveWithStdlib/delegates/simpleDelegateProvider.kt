// RUN_PIPELINE_TILL: BACKEND
class Delegate(val value: String) {
    operator fun getValue(thisRef: Any?, property: Any?) = value
}

class DelegateProvider(val value: String) {
    operator fun provideDelegate(thisRef: Any?, property: Any?) = Delegate(value)
}

val testTopLevel by DelegateProvider("OK")

/* GENERATED_FIR_TAGS: classDeclaration, functionDeclaration, nullableType, operator, primaryConstructor,
propertyDeclaration, propertyDelegate, stringLiteral */
