// RUN_PIPELINE_TILL: FRONTEND
// CHECK_TYPE

class Outer<E> {
    inner open class InnerBase<F>
    inner class Inner<H> : InnerBase<H>() {
        val prop: E = null!!
    }

    fun foo(x: InnerBase<String>, y: Any?, z: Outer<*>.InnerBase<String>) {
        if (x is Inner) {
            <!DEBUG_INFO_SMARTCAST!>x<!>.prop.checkType { _<E>() }
        }

        if (y is <!NO_TYPE_ARGUMENTS_ON_RHS!>Inner<!>) return

        if (z is Inner) {
            z.prop.checkType { _<Any?>() }
            return
        }

        if (y is Outer<*>.Inner<*>) {
            <!DEBUG_INFO_SMARTCAST!>y<!>.prop.checkType { _<Any?>() }
        }
    }

    fun bar(x: InnerBase<String>, y: Any?, z: Outer<*>.InnerBase<String>) {
        x as Inner
        y as <!NO_TYPE_ARGUMENTS_ON_RHS!>Inner<!>
        z as Inner
    }
}

/* GENERATED_FIR_TAGS: asExpression, capturedType, checkNotNullCall, classDeclaration, funWithExtensionReceiver,
functionDeclaration, functionalType, ifExpression, infix, inner, isExpression, lambdaLiteral, nullableType,
propertyDeclaration, smartcast, starProjection, typeParameter, typeWithExtension */
