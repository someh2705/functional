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

class TakeSequence<T>
constructor(
    private val sequence: Sequence<T>,
    private val count: Int
) : Sequence<T> {
    init {
        require(count >= 0) { "count must be non-negative, but was $count" }
    }

    override fun iterator(): Iterator<T> = object : Iterator<T> {
        var left = count
        var iterator = sequence.iterator()

        override fun next(): T {
            if (left == 0) {
                throw NoSuchElementException()
            }
            left--
            return iterator.next()
        }

        override fun hasNext(): Boolean {
            return left > 0 && iterator.hasNext()
        }
    }

}

fun <T> takeL(sequence: Sequence<T>, n: Int): Sequence<T> {
    require(n >= 0) { "Requested element count $n is less than zero." }
    return TakeSequence(sequence, n)
}

fun <T> takeL(n: Int): (Sequence<T>) -> Sequence<T> {
    return { sequence: Sequence<T> ->
        takeL(sequence, n)
    }
}