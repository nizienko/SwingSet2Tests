package engine.helpers

import org.fest.swing.core.MouseButton
import org.fest.swing.core.Robot
import java.awt.Point
import java.math.BigDecimal
import java.math.RoundingMode
import kotlin.math.absoluteValue

fun Robot.moveMouseGradually(from: Point, to: Point, skippedPixels: Int = 1) {
    val xLength = (to.x - from.x).absoluteValue
    val yLength = (to.y - from.y).absoluteValue

    val k = try {
        BigDecimal(from.y - to.y).divide(BigDecimal(from.x - to.x), 2, RoundingMode.DOWN)
    } catch (e: ArithmeticException) { BigDecimal.ZERO }

    val b = BigDecimal(from.y).subtract(k.multiply(BigDecimal(from.x)))

    if (xLength > yLength) {
        val yFun: (x: Int) -> Int = { k.multiply(BigDecimal(it)).add(b).toInt() }
        for (x in from.x goingTo to.x step skippedPixels) {
            moveMouse(x, yFun(x))
        }
    } else {
        val xFun: (y: Int) -> Int = if (k != BigDecimal.ZERO) {
            { BigDecimal(it).subtract(b).divide(k, 0, RoundingMode.DOWN).toInt() }
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