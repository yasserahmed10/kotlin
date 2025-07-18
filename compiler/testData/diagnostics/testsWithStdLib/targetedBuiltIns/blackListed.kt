// RUN_PIPELINE_TILL: FRONTEND
// FIR_IDENTICAL
// FULL_JDK

abstract class A : MutableList<String> {
    override fun sort(/*0*/ p0: java.util.Comparator<in String>) {
        super.sort(p0)
    }
}

fun foo(x: MutableList<String>, y: java.util.ArrayList<String>, z: A, p: java.util.Comparator<in String>) {
    x.<!DEPRECATION_ERROR!>sort<!>(p)
    y.<!DEPRECATION_ERROR!>sort<!>(p)

    z.<!DEPRECATION_ERROR!>sort<!>(p)
}

fun bar(x: MutableList<String>, y: java.util.ArrayList<String>, z: A) {
    x.<!DEPRECATION_ERROR!>sort<!> { a, b -> a.length - b.length }
    y.<!DEPRECATION_ERROR!>sort<!> { a, b -> a.length - b.length }

    z.<!DEPRECATION_ERROR!>sort<!> { a, b -> a.length - b.length }
}

/* GENERATED_FIR_TAGS: additiveExpression, classDeclaration, functionDeclaration, inProjection, lambdaLiteral, override,
superExpression */
