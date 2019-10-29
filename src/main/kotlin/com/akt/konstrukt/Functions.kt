package com.akt.konstrukt

fun <INPUT, INTERMEDIATE, OUTPUT> compose(
        f: (INPUT) -> INTERMEDIATE,
        g: (INTERMEDIATE) -> OUTPUT
        ): (INPUT) -> OUTPUT = { x -> g(f(x)) }

fun <INPUT, INTERMEDIATE, OUTPUT> composeCurried(): ((INPUT) -> INTERMEDIATE) -> ((INTERMEDIATE) -> OUTPUT) -> ((INPUT) -> OUTPUT) = { f ->
    { g ->
        {
            g(f(it))
        }
    }
}