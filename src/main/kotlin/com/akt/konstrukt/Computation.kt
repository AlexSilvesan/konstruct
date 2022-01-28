package com.akt.konstrukt

/**
 * Describes a computation that can either produce a result or throws a failure.
 */
sealed class Computation<T, FAILURE> where FAILURE : Throwable {

    /**
     * @return the result of the computation if successful, null otherwise.
     */
    abstract fun getResult(): T?

    /**
     * @return the failure that occurred during the computation if failed, null otherwise.
     */
    abstract fun getFailure(): FAILURE?

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
    abstract fun consume(resultConsumer: (T) -> Unit, failureConsumer: (FAILURE) -> Unit)
}

class SuccessfulComputation<T, FAILURE : Throwable> (private val result: T) : Computation<T, FAILURE>() {
    override fun getResult() = result

    override fun getFailure(): FAILURE? = null

    override fun getResultOrElse(fallbackResultSupplier: () -> T) = result

    override fun consume(resultConsumer: (T) -> Unit, failureConsumer: (FAILURE) -> Unit) = resultConsumer.invoke(result)
}

class FailedComputation<T, FAILURE : Throwable>(private val failure: FAILURE) : Computation<T, FAILURE>() {
    override fun getResult(): T? = null

    override fun getFailure() = failure

    override fun getResultOrElse(fallbackResultSupplier: () -> T) = fallbackResultSupplier.invoke()

    override fun consume(resultConsumer: (T) -> Unit, failureConsumer: (FAILURE) -> Unit) = failureConsumer.invoke(failure)
}