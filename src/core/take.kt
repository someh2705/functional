package core

fun <T> take(
    iterable: Iterable<T>,
    n: Int
): Iterable<T> {
    require(n >= 0) { "Requested element count $n is less than zero." }
    if (n == 0) {
        return emptyList()
    }

    val list = arrayListOf<T>()
    for ((count, item) in iterable.withIndex()) {
        list.add(item)
        if (count + 1 == n) {
            break
        }
    }

    return list.asIterable()
}

fun <T> take(n: Int): (Iterable<T>) -> Iterable<T> {
    return { iterable: Iterable<T> ->
        take(iterable, n)
    }
}