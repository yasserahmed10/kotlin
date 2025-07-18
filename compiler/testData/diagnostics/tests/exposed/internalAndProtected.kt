// RUN_PIPELINE_TILL: BACKEND
// FIR_IDENTICAL
public open class A {
    protected open class B
}

public open class C : A() {
    protected open class D {
        // internal & protected(in C) <= protected(in A): Ok
        internal open class E : A.B()
    }
}

/* GENERATED_FIR_TAGS: classDeclaration, nestedClass */
