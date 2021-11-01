package core

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