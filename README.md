This library allows dealing with computations that either return a result or throw an exception.

It is meant as an alternative to propagating exceptions by providing handlers for successful or failed computation.

```kotlin
fun fooSuccess(): Computation<String> = SuccessfulComputation("abc")

fun fooFailure(): Computation<String> = FailedComputation(Exception())
```

# Using the library

1. Using java-style consumers

```kotlin
fun consumer() {
    val computation = foo()

    computation.consume(
            { result -> println(result) },
            { failure -> failure.printStackTrace() }
    )
}
```


2. Using a supplier that provides another value which is used only in case of failure

```kotlin
fun getResultOrElse() {
    val resultForSuccess = fooSuccess().getResultOrElse { "def" }
    val resultForFailure = fooFailure().getResultOrElse { "def" }

    assert(resultForSuccess == "abc")
    assert(resultForFailure == "def")
}
```


3. Using Pattern matching

```kotlin
fun patternMatching() {
    val computation = foo()

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

```

# Building

Run the gradle build in order to add the artifact to maven local repository

``` gradle
gradlew clean assemble publishToMavenLocal
```

Reference it in the maven build

``` xml
<dependency>
    <groupId>com.akt</groupId>
    <artifactId>konstrukt</artifactId>
    <version>0.1</version>
</dependency>
```

Or in the gradle build

``` gradle
compile 'com.akt:konstrukt:0.1'
```