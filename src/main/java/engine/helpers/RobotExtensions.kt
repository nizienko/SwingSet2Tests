package engine.helpers

import org.fest.swing.core.MouseButton
import org.fest.swing.core.Robot
import java.awt.Point
import kotlin.math.absoluteValue

/**
 * Move mouse from point to point.
 * It is possible to manipulate speed by defining skippedPixels parameter.
 *
 * @param from starting point
 * @param to finishing at that point
 * @param skippedPixels 1 by default - pointer will move to every pixel of path from start point to finish.
 *  With values > 1 mouse will jump what will increase speed
 */
fun Robot.moveMouseGradually(from: Point, to: Point, skippedPixels: Int = 1) {
    val xLength = (to.x - from.x).absoluteValue
    val yLength = (to.y - from.y).absoluteValue

    val k = ((from.y - to.y).toDouble()) / (from.x - to.x)

    val b = from.y - k * from.x

    if (xLength > yLength) {
        val yFun: (x: Int) -> Int = { (k * it + b).toInt() }
        for (x in from.x goingTo to.x step skippedPixels) {
            moveMouse(x, yFun(x))
        }
    } else {
        val xFun: (y: Int) -> Int = if (k != 0.0) {
            { it - (b / k).toInt() }
        } else {
            { from.x }
        }
        for (y in from.y goingTo to.y step skippedPixels) {
            moveMouse(xFun(y), y)
        }
    }
    moveMouse(to)
}


fun Robot.pressingMouse(button: MouseButton, block: Robot.() -> Unit) {
    this.pressMouse(button)
    block()
    this.releaseMouse(button)
}

fun Robot.pressingKey(keyCode: Int, block: Robot.() -> Unit) {
    this.pressKey(keyCode)
    block()
    this.releaseKey(keyCode)
}

infix fun Int.goingTo(x2: Int): IntProgression =
        if (this < x2) {
            this..x2
        } else {
            this downTo x2
        }