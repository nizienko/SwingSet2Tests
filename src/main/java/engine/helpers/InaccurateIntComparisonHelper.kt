package engine.helpers

data class ShouldBeBetweenAssertion(val value: Int, val from: Int)

infix fun Int.shouldBeBetween(expected: Int) = ShouldBeBetweenAssertion(this, expected)
infix fun ShouldBeBetweenAssertion.and(to: Int) {
    if (value < from || value > to) {
        throw AssertionError("$value is not between $from and $to")
    }
}

