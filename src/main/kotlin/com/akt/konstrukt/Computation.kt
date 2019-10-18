package com.akt.konstrukt

import java.lang.IllegalStateException

/**
 * Describes a computation that can either produce a result or throws a failure.
 */
sealed class Computation<T> {

    /**
     * @return the result of the computation.
     *
     * @throws IllegalStateException if the computation failed and there is no result.
     */
    abstract fun getResult(): T

    /**
     * @return the failure that occurred during the computation.
     *
     * @throws IllegalStateException if the computation was successful and there is no failure.
     */
    abstract fun getFailure(): Throwable

    /**
     * Allows providing a lazy supplier which can provide a fallback value. The fallback supplier is not called in case of successful computation.
     *
     * @return the computation result if the computation was successful; otherwise, the fallback value is returned.
     */
    abstract fun getResultOrElse(fallbackResultSupplier: () -> T): T

    /**
     * Allows providing 2 suppliers, 1 for result and 1 for failure.
     *
     * For a given, only the corresponding supplier will be called.
     */
    abstract fun consume(resultConsumer: (T) -> Unit, failureConsumer: (Throwable) -> Unit)
}

class SuccessfulComputation<T>(private val result: T) : Computation<T>() {
    override fun getResult() = result

    override fun getFailure() = throw IllegalStateException("Successful computation does not have a failure")

    override fun getResultOrElse(fallbackResultSupplier: () -> T) = result

    override fun consume(resultConsumer: (T) -> Unit, failureConsumer: (Throwable) -> Unit) = resultConsumer.invoke(result)
}

class FailedComputation<T>(private val failure: Throwable) : Computation<T>() {
    override fun getResult() = throw IllegalStateException("Failed computation does not have a result")

    override fun getFailure() = failure

    override fun getResultOrElse(fallbackResultSupplier: () -> T) = fallbackResultSupplier.invoke()

    override fun consume(resultConsumer: (T) -> Unit, failureConsumer: (Throwable) -> Unit) = failureConsumer.invoke(failure)
}