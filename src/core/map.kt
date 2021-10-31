package core

fun <T> map(iterable: Iterable<T>, transform: (T) -> T): Iterable<T> {
    val iterator = iterable.iterator()
    val new = arrayListOf<T>()

    while (iterator.hasNext()) {
        new.add(transform(iterator.next()))
    }

    return new.asIterable()
}