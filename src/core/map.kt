package core

fun <T, R> map(iterable: Iterable<T>, transform: (T) -> R): Iterable<R> {
    val iterator = iterable.iterator()
    val new = arrayListOf<R>()

    while (iterator.hasNext()) {
        new.add(transform(iterator.next()))
    }

    return new.asIterable()
}

fun <T, R> map(transform: (T) -> R): (Iterable<T>) -> Iterable<R> {
    return { iterable: Iterable<T> ->
        map(iterable, transform)
    }
}

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