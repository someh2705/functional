package core

fun <T, R> fold(iterable: Iterable<T>, initial: R, operation: (acc: R, T) -> R): R {
    val iterator = iterable.iterator()
    var accumulator = initial

    while (iterator.hasNext()) {
        val element = iterator.next()
        accumulator = operation(accumulator, element)
    }
    return accumulator
}