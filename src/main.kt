import core.*
import kotlin.system.measureTimeMillis

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