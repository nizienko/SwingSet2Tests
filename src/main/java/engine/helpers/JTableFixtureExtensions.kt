package engine.helpers

import engine.helpers.ColumnHeaderPart.*
import org.fest.swing.awt.AWT.translate
import org.fest.swing.core.MouseButton.LEFT_BUTTON
import org.fest.swing.fixture.JTableFixture
import java.awt.Point
import java.math.BigDecimal
import java.math.RoundingMode

enum class ColumnHeaderPart {
    CENTER, LEFT, RIGHT, TOP, BOTTOM
}

fun JTableFixture.moveMouseToColumnHeader(index: Int, part: ColumnHeaderPart = CENTER) =
        with(tableHeader().component()) {
            robot.moveMouse(getColumnHeaderPoint(index, part))
        }

fun JTableFixture.getColumnHeaderPoint(index: Int, part: ColumnHeaderPart = CENTER): Point =
        with(tableHeader().component()) {
            val r = getHeaderRect(index)
            when (part) {
                CENTER -> {
                    val p = Point(r.x + r.width / 2, r.y + r.height / 2)
                    return translate(this, p.x, p.y)
                }
                LEFT -> {
                    val p = Point(r.x, r.y + r.height / 2)
                    return translate(this, p.x, p.y)
                }
                RIGHT -> {
                    val p = Point(r.x + r.width, r.y + r.height / 2)
                    return translate(this, p.x, p.y)
                }
                TOP -> {
                    val p = Point(r.x + r.width / 2, r.y)
                    return translate(this, p.x, p.y)
                }
                BOTTOM -> {
                    val p = Point(r.x + r.width / 2, r.y + r.height)
                    return translate(this, p.x, p.y)
                }
            }
        }

fun JTableFixture.dragColumnToAnother(fromIndex: Int, toIndex: Int) {
    moveMouseToColumnHeader(fromIndex)
    robot.pressingMouse(LEFT_BUTTON) {
        moveMouseGradually(
                from = getColumnHeaderPoint(fromIndex),
                to = getColumnHeaderPoint(toIndex),
                skippedPixels = 100)
    }
}

fun JTableFixture.getColumnNames(): List<String> =
        tableHeader().component().columnModel.columns.toList()
                .map { it.headerValue.toString() }

fun JTableFixture.getColumnsWidth(): List<Int> =
        (0 until this.tableHeader().component().columnModel.columnCount).map {
            this.tableHeader().component().getHeaderRect(it).width
        }

fun JTableFixture.columnsSizeRatio(): List<BigDecimal> {
    val columnsCount = this.tableHeader().component().columnModel.columnCount
    val columnsSize = (0 until columnsCount).map {
        this.tableHeader().component().getHeaderRect(it).width
    }.sum()
    return (0 until columnsCount).map {
        BigDecimal(this.tableHeader().component().getHeaderRect(it).width)
                .divide((BigDecimal(columnsSize)
                        .divide(BigDecimal(columnsCount), 5, RoundingMode.HALF_UP)), 5, RoundingMode.HALF_UP)
    }
}

fun Int.applyRatio(ratio: BigDecimal) = BigDecimal(this).multiply(ratio).setScale(0, RoundingMode.HALF_UP).toInt()
