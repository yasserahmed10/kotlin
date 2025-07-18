// RUN_PIPELINE_TILL: FRONTEND
// FILE: Foo.java
public class Foo {
    public String getBar() { return ""; }
    protected void setBar(String x) {  }
    public String getFoo() { return ""; }
    private void setFoo(String x) {  }
}

// FILE: main.kt

class Data(var x: Foo)

class B : Foo() {
    fun baz(a: Foo, t: Foo, d: Data) {
        a.bar = t.bar
        a.<!INVISIBLE_SETTER!>foo<!> = t.foo

        if (d.x is B) {
            d.x.bar = d.x.bar + ""
            d.x.<!INVISIBLE_SETTER!>foo<!> = d.x.foo + ""
        }
    }
}

/* GENERATED_FIR_TAGS: additiveExpression, assignment, classDeclaration, flexibleType, functionDeclaration, ifExpression,
isExpression, javaProperty, javaType, primaryConstructor, propertyDeclaration, smartcast, stringLiteral */
