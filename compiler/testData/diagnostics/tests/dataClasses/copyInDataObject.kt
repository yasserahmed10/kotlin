// RUN_PIPELINE_TILL: FRONTEND
// ISSUE: KT-65408

data object CreateBuilder : Builder<CreateBuilder> {
    fun foo(): CreateBuilder = copy() // should be an error
}

interface Builder<T> : Cloneable

/* GENERATED_FIR_TAGS: data, functionDeclaration, interfaceDeclaration, nullableType, objectDeclaration, typeParameter */
