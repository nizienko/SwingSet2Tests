package engine.helpers


infix fun Int.goingTo(x2: Int): IntProgression =
        if (this < x2) {
            this..x2
        } else {
            this downTo x2
        }
