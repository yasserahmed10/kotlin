// RUN_PIPELINE_TILL: FRONTEND
fun foo(x: String?, y: String?, z: String?, w: String?) {
    if (x != null && y != null && (x == z || y == z))
        z.length
    else
        z<!UNSAFE_CALL!>.<!>length
    if (x != null || y != null || (x != z && <!SENSELESS_COMPARISON!>y != z<!>))
        z<!UNSAFE_CALL!>.<!>length
    else
        z<!UNSAFE_CALL!>.<!>length
    if (x == null || y == null || (x != z && y != z))
        z<!UNSAFE_CALL!>.<!>length
    else
        z.length
    if (x != null && y == x && z == y && w == z)
        w.length
    else
        w<!UNSAFE_CALL!>.<!>length
    if ((x != null && y == x) || (z != null && y == z))
        y.length
    else
        y<!UNSAFE_CALL!>.<!>length
}

/* GENERATED_FIR_TAGS: andExpression, disjunctionExpression, equalityExpression, functionDeclaration, ifExpression,
nullableType, smartcast */
