package com.example.materialcalculator.domain

enum class Operation(val symbol: Char) {
    ADD('+'),
    SUBTRACT('-'),
    MULTIPLY('x'),
    DIVIDE('/'),
    PERCENT('%'),
}

val operationSymbols = Operation
    .values()
    .map { operation ->
        operation.symbol
    }.joinToString("")

fun operationFromSymbol(symbol: Char): Operation {
    return Operation.values().find { operation ->
        operation.symbol == symbol
    } ?: throw IllegalArgumentException("Invalid symbol")
}