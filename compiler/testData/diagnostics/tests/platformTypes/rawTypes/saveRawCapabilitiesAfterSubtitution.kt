// DISABLE_JAVA_FACADE
// RUN_PIPELINE_TILL: BACKEND
// FIR_IDENTICAL
// DIAGNOSTICS: -UNUSED_VARIABLE
// FILE: A.java

import java.util.*;

class A<T> {
    public class Inner<E> {
        List<T> foo() {}
    }
}

// FILE: Test.java

class Test {
    static A rawAField = null;
}

// FILE: main.kt

val result = Test.rawAField.Inner<Double>().foo()

/* GENERATED_FIR_TAGS: flexibleType, javaProperty, javaType, nullableType, propertyDeclaration */
