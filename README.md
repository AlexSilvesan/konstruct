# Using the library

## Computation

This library allows dealing with computations that either return a result or throw an exception.

It is meant as an alternative to propagating exceptions by providing handlers for successful or failed computation.

```kotlin
fun fooSuccess(): Computation<String> = SuccessfulComputation("abc")

fun fooFailure(): Computation<String> = FailedComputation(Exception())
```

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

## Composing Functions

This library provides a way to compose functions:
 
Having `f: A -> B, g: B -> C, h: A -> C`  where h(x) = g(f(x)) you can build h function using _compose_ or _composeCurried_ from Functions

```
val f : (Int) -> Boolean = { it > 0 && ((it and (it - 1)) == 0)} // is power of 2
val g : (Boolean) -> String = { it.toString().toUpperCase() } // boolean to Uppercase
```

Then we can use the Functions utilities to compose the above functions

```
fun compose() {
    val h = compose(f, g)
    println ( h(5) )
    println ( h(16) )
}

fun composeCurried() {
    val h = composeCurried<Int, Boolean, String>()(f)(g)
    println ( h(32) )
    println ( h(160) )
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