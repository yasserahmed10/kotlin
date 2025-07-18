// RUN_PIPELINE_TILL: BACKEND
// FIR_IDENTICAL
// FILE: p/SAM.java

package p;

public interface SAM<R> {
    R foo();
}

// FILE: p/Util.java

package p;

public class Util {

    public static void sam(SAM<Void> sam) {}
}

// FILE: k.kt

import p.*

fun test() {
    Util.sam {
        null
    }
}

/* GENERATED_FIR_TAGS: flexibleType, functionDeclaration, javaFunction, javaType, lambdaLiteral, samConversion */
