// RUN_PIPELINE_TILL: BACKEND
// FIR_IDENTICAL
//KT-2226 Parameter used as delegation by object marked as unused
package a

interface A {
    fun foo() : Int
}

class B : A {
    override fun foo() = 10
}
fun foo(b: B) : Int {
    val o = object : A by b {
    }
    return o.foo()
}

/* GENERATED_FIR_TAGS: anonymousObjectExpression, classDeclaration, functionDeclaration, inheritanceDelegation,
integerLiteral, interfaceDeclaration, localProperty, override, propertyDeclaration */
