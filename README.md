# I study lazy evaluation use Kotlin

Iterable and Sequence base and not extension function.

I studied lazy evaluation and little bit functional programming technic.

I tried to implement legacy Kotlin code as much as possible, and the logic is almost similar to built-in functions.

## map

map function transform T to R and return new Iterable or Sequence.

Iterable base function take all iterator and return new Iterable.

Sequence base function doesn't take iterator. just return new overried Iterator.

#### Iterable base

``` kotlin
fun <T, R> map(iterable: Iterable<T>, transform: (T) -> R): Iterable<R> {
    val iterator = iterable.iterator()
    val new = arrayListOf<R>()

    while (iterator.hasNext()) {
        new.add(transform(iterator.next()))
    }

    return new.asIterable()
}

// Apply currying for use in go and pipe functions
fun <T, R> map(transform: (T) -> R): (Iterable<T>) -> Iterable<R> {
    return { iterable: Iterable<T> ->
        map(iterable, transform)
    }
}

```

#### Sequence base

``` kotlin
class TransformSequence<T, R>
constructor(private val sequence: Sequence<T>, private val transform: (T) -> R) : Sequence<R> {
    override fun iterator(): Iterator<R> = object : Iterator<R> {
        val iterator = sequence.iterator()
        override fun next(): R {
            return transform(iterator.next())
        }

        override fun hasNext(): Boolean {
            return iterator.hasNext()
        }
    }
}

fun <T, R> mapL(sequence: Sequence<T>, transform: (T) -> R): Sequence<R> {
    return TransformSequence(sequence, transform)
}

fun <T, R> mapL(transform: (T) -> R): (Sequence<T>) -> Sequence<R> {
    return { sequence: Sequence<T> ->
        mapL(sequence, transform)
    }
}
```

## go

A pipeline is implemented to make function composition easier.

if **curried** map or etc. exist, it doesn't have to lambda function like { iterable: Iterable<T> -> map(iterable) { it * 2 } }.

```kotlin
fun <T> go(
    iterable: Iterable<T>,
    vararg functions: (Iterable<T>) -> Iterable<T>,
    last: ((Iterable<T>) -> Any)? = null
): Any {
    val result = fold(functions.asIterable(), iterable) { acc, function ->
        function(acc)
    }

    if (last != null) {
        return last(result)
    }

    return result
}
```

## Usage
```kotlin
fun main() {
    val thousand = (0..1000).toList()
    val million = (0..100000000).toList()

    val pipes = pipe<Int>(
        filter { it % 2 == 0 },
        map { it * 2 },
        take(5)
    ) {
        println("Iterable pipe result: ${reduce(it) { a, b -> a + b }}")
    }

    val pipesL = pipe<Int>(
        filterL { it % 2 == 0 },
        mapL { it * 2 },
        takeL(5),
    ) {
        println("Sequence pipe result: ${reduce(it) { a, b -> a + b }}")
    }

    println("Thousand iterator test")

    var iterable = measureTimeMillis {
        pipes(thousand)
    }
    println("Iterable pipe time: $iterable")

    println()

    var sequence = measureTimeMillis {
        pipesL(thousand.asSequence())
    }
    println("Sequence pipe time: $sequence")

    println()

    println("Million iterator test")

    iterable = measureTimeMillis {
        pipes(million)
    }
    println("Iterable pipe time: $iterable")

    println()

    sequence = measureTimeMillis {
        pipesL(million.asSequence())
    }
    println("Sequence pipe time: $sequence")

}

/*
* Thousand iterator test
* Iterable pipe result: 40
* Iterable pipe time: 30
* 
* Sequence pipe result: 40
* Sequence pipe time: 6
* 
* Million iterator test
* Iterable pipe result: 40
* Iterable pipe time: 6578
* 
* Sequence pipe result: 40
* Sequence pipe time: 0
 */
```
