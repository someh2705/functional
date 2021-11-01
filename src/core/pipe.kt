package core

@JvmName("iterable pipe")
fun <T> pipe(
    vararg functions: (Iterable<T>) -> Iterable<T>,
    last: ((Iterable<T>) -> Any)? = null
): (Iterable<T>) -> Any {
    return { iterable: Iterable<T> ->
        go(
            iterable,
            *functions
        ) {
            if (last != null) {
                last(it)
            } else {
                it
            }
        }
    }
}

@JvmName("sequence pipe")
fun <T> pipe(
    vararg functions: (Sequence<T>) -> Sequence<T>,
    last: ((Sequence<T>) -> Any)? = null
): (Sequence<T>) -> Any {
    return { sequence: Sequence<T> ->
        go(
            sequence,
            *functions
        ) {
            if (last != null) {
                last(it)
            } else {
                it
            }
        }
    }
}