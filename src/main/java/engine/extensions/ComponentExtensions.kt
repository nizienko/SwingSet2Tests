package engine.extensions

import org.fest.swing.awt.AWT.translate
import org.fest.swing.core.MouseButton.LEFT_BUTTON
import org.fest.swing.fixture.JTableFixture
import java.awt.Point


fun JTableFixture.moveMouseToColumnHeader(index: Int) = with(tableHeader().component()) {
    robot.moveMouse(getColumnHeaderPoint(index))
}

fun JTableFixture.getColumnHeaderPoint(index: Int): Point = with(tableHeader().component()) {
    val r = getHeaderRect(index)
    val p = Point(r.x + r.width / 2, r.y + r.height / 2)
    return translate(this, p.x, p.y)
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
                .map { it.headerValue.toString() }.toList()

