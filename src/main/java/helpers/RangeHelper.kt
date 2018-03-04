package helpers

fun range(x1: Int, x2: Int, stepSize: Int): IntProgression =
        if (x1 < x2) {
            x1..x2 step stepSize
        } else {
            x1 downTo x2 step stepSize
        }
