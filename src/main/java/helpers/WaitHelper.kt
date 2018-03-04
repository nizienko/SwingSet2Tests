package helpers

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