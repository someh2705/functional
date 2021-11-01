package core

fun <T> filter(iterable: Iterable<T>, predicate: (T) -> Boolean): Iterable<T> {
    val iterator = iterable.iterator()
    val new = arrayListOf<T>()

    while (iterator.hasNext()) {
        val element = iterator.next()
        if (predicate(element)) {
            new.add(element)
        }
    }

    return new.asIterable()
}

fun <T> filter(predicate: (T) -> Boolean): (Iterable<T>) -> Iterable<T> {
    return { iterable: Iterable<T> ->
        filter(iterable, predicate)
    }
}

class FilterSequence<T>
constructor(
    private val sequence: Sequence<T>,
    private val sendWhen: Boolean = true,
    private val predicate: (T) -> Boolean
) : Sequence<T> {

    override fun iterator(): Iterator<T> = object : Iterator<T> {
        val iterator = sequence.iterator()
        var nextState: Int = -1
        var nextItem: T? = null

        private fun calcNext() {
            while (iterator.hasNext()) {
                val item = iterator.next()
                if (predicate(item) == sendWhen) {
                    nextItem = item
                    nextState = 1
                    return
                }
            }
            nextState = 0
        }

        override fun next(): T {
            if (nextState == -1) {
                calcNext()
            }
            if (nextState == 0) {
                throw NoSuchElementException()
            }
            val result = nextItem
            nextItem = null
            nextState = -1
            @Suppress("UNCHECKED_CAST")
            return result as T

        }

        override fun hasNext(): Boolean {
            if (nextState == -1) {
                calcNext()
            }
            return nextState == 1
        }
    }
}

fun <T> filterL(sequence: Sequence<T>, predicate: (T) -> Boolean): Sequence<T> {
    return FilterSequence(sequence, true, predicate)
}

fun <T> filterL(predicate: (T) -> Boolean): (Sequence<T>) -> Sequence<T> {
    return { sequence: Sequence<T> ->
        filterL(sequence, predicate)
    }
}