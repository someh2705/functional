package core

fun <S, T : S> reduce(iterable: Iterable<T>, operation: (acc: S, T) -> T): T {
    val iterator = iterable.iterator()
    if (!iterator.hasNext()) throw Exception("Empty iterable can't be reduce")

    var accumulator = iterator.next()

    while (iterator.hasNext()) {
        val element = iterator.next()
        accumulator = operation(accumulator, element)
    }

    return accumulator
}