// RUN_PIPELINE_TILL: BACKEND
// DIAGNOSTICS: -UNNECESSARY_SAFE_CALL -SAFE_CALL_WILL_CHANGE_NULLABILITY

// MODULE: m1
// FILE: a.kt
package p

public interface B {
    public fun getParent(): B?
}

// MODULE: m2(m1)
// FILE: b.kt
package p

public interface C : B {
    override fun getParent(): B?

}

// MODULE: m3
// FILE: b.kt
package p

public interface B {
    public fun getParent(): Int
}

// MODULE: m4(m3, m2)
// FILE: c.kt
import p.*

fun test(b: B?) {
    if (b is C) {
        b?.<!OVERLOAD_RESOLUTION_AMBIGUITY!>getParent<!>()
    }
}

/* GENERATED_FIR_TAGS: functionDeclaration, ifExpression, interfaceDeclaration, isExpression, nullableType, override,
safeCall, smartcast */
