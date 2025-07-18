// RUN_PIPELINE_TILL: BACKEND
// FIR_IDENTICAL
// DIAGNOSTICS: -UNUSED_PARAMETER
// JAVAC_EXPECTED_FILE
// FILE: VcsException.java
import org.jetbrains.annotations.NotNull;

public class VcsException extends Exception {
    @Override
    @NotNull
    public String getMessage() {
        return "";
    }
}

// FILE: main.kt
fun foo(e: VcsException) {
    e.message.contains("")
    "" in e.message
}

public operator fun CharSequence.contains(other: CharSequence): Boolean = true

/* GENERATED_FIR_TAGS: funWithExtensionReceiver, functionDeclaration, javaProperty, javaType, operator, stringLiteral */
