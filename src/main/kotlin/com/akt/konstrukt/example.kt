package com.akt.konstrukt

import java.lang.IllegalStateException

fun main() {
//    lambdas()
//    getResultOrElse()
//    patternMatching()
    compose()
    composeCurried()
}


fun compose() {
    val composedFunctions = compose(isPowerOf2, toUppercase)
    println ( composedFunctions(5) )
    println ( composedFunctions(16) )
}

fun composeCurried() {
    val composeFunctionsCurried = composeCurried<Int, Boolean, String>()(isPowerOf2)(toUppercase)
    println ( composeFunctionsCurried(32) )
    println ( composeFunctionsCurried(160) )
}


private fun consumer() {
    val computation = fooFailure()

    computation.consume(
            { result -> println(result) },
            { failure -> failure.printStackTrace() }
    )
}

private fun getResultOrElse() {
    val resultForSuccess = fooSuccess().getResultOrElse { "def" }
    val resultForFailure = fooFailure().getResultOrElse { "def" }

    assert(resultForSuccess == "abc")
    assert(resultForFailure == "def")
}

private fun patternMatching() {
    val computation = fooSuccess()

    when (computation) {
        is SuccessfulComputation -> {
            val result = computation.getResult()
            println(result)
        }
        is FailedComputation -> {
            val failure = computation.getFailure()
            failure.printStackTrace()
        }
    }
}

private fun fooSuccess(): Computation<String, IllegalStateException> = SuccessfulComputation("abc")

private fun fooFailure(): Computation<String, IllegalStateException> = FailedComputation(IllegalStateException())

val isPowerOf2 : (Int) -> Boolean = { it > 0 && ((it and (it - 1)) == 0)}
val toUppercase : (Boolean) -> String = { it.toString().toUpperCase() }