package core

fun <T> map(iterable: Iterable<T>, f: (T) -> T): Iterable<T> {
    val iterator = iterable.iterator()
    val new = arrayListOf<T>()

    while (iterator.hasNext()) {
        new.add(iterator.next())
    }

    return new.asIterable()
}