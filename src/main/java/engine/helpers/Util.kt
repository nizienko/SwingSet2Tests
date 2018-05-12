package engine.helpers


data class ComparedLists<out T>(val list1: List<T>, val list2: List<T>)

infix fun <T> List<T>.comparingWith(list2: List<T>) = ComparedLists(this, list2)
infix fun <T> ComparedLists<T>.shouldHasEqualValuesOn(map: Map<Int, Int>) {
    val errors = StringBuilder()
    map.forEach { index1, index2 ->
        if (list1[index1] != list2[index2]) {
            errors.append("Failed when expected that '${list1[index1]}' on $index1 " +
                    "is equal to '${list2[index2]}' on $index2\n")
        }
    }
    if (errors.isNotEmpty()) {
        throw AssertionError(errors.toString())
    }
}


data class ShouldBeBetweenAssertion(val value: Int, val from: Int)

infix fun Int.shouldBeBetween(expected: Int) = ShouldBeBetweenAssertion(this, expected)
infix fun ShouldBeBetweenAssertion.and(to: Int) {
    if (value < from || value > to) {
        throw AssertionError("$value is not between $from and $to")
    }
}


/**
 *  Function will try to execute lambda block until timeout expired
 *  @param timeMillis - timeout
 *  @param block - lambda
 */
fun <A> waiting(timeMillis: Long = 10000, block: () -> A): A {
    val startTime = System.currentTimeMillis()
    while (System.currentTimeMillis() < startTime + timeMillis) {
        try {
            return block()
        } catch (e: Throwable) {
            Thread.sleep(100)
        }
    }
    return block()
}