// TARGET_BACKEND: WASM

// RUN_THIRD_PARTY_OPTIMIZER
// WASM_DCE_EXPECTED_OUTPUT_SIZE: wasm 17_254
// WASM_DCE_EXPECTED_OUTPUT_SIZE:  mjs  4_804
// WASM_OPT_EXPECTED_OUTPUT_SIZE:       2_828

fun box() = "OK"