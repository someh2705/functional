package core

fun <T> go(
    iterable: Iterable<T>,
    vararg functions: (Iterable<T>) -> Iterable<T>,
    last: ((Iterable<T>) -> Any)? = null
): Any {
    val result = fold(functions.asIterable(), iterable) { acc, function ->
        function(acc)
    }

    if (last != null) {
        return last(result)
    }

    return result
}

fun <T> go(
    sequence: Sequence<T>,
    vararg functions: (Sequence<T>) -> Sequence<T>,
    last: ((Sequence<T>) -> Any)? = null
): Any {
    val result = fold(functions.asSequence(), sequence) { acc, function ->
        function(acc)
    }

    if (last != null) {
        return last(result)
    }

    return result
}