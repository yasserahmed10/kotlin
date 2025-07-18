// RUN_PIPELINE_TILL: FRONTEND
// DIAGNOSTICS: -UNUSED_VARIABLE -UNUSED_PARAMETER

// FILE: JSam.java

public interface JSam<T, R> {
    R apply(T t);
}

// FILE: Inv.java

public class Inv<T> {
    public final <R> Inv<R> map(JSam<? super T, ? extends R> mapper) {
        return null;
    }
}

// FILE: test.kt

fun test(inv: Inv<String>) {
    val m: ((String) -> String) -> Inv<String> = inv::map
    take(inv::map)
}

fun take(f: ((String) -> String) -> Inv<String>) {}

/* GENERATED_FIR_TAGS: functionDeclaration, functionalType, javaType, localProperty, propertyDeclaration */
