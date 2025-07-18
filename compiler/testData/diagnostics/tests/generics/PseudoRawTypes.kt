// RUN_PIPELINE_TILL: BACKEND
// FIR_IDENTICAL
// JAVAC_EXPECTED_FILE
// FILE: java/util/Collection.java
package java.util;

public class Collection {
  public void foo() {}
}

// FILE: test/Usage.java
package test;

import java.util.*;

public class Usage {
  void foo(Collection c) {
    c.foo();
  }
}

// FILE: Kotlin.kt
package test

fun foo(u: Usage) {
  u.foo(null)
}

/* GENERATED_FIR_TAGS: functionDeclaration, javaFunction, javaType, nullableType */
