package engine.extensions

import org.fest.swing.core.MouseButton
import org.fest.swing.fixture.JTableFixture
import java.awt.Point

fun JTableFixture.moveMouseToColumnHeader(index: Int) = with(tableHeader().component()) {
    val r = getHeaderRect(index)
    val p = Point(r.x + r.width / 2, r.y + r.height / 2)
    robot.moveMouse(this, p)
}

fun JTableFixture.dragColumn(fromIndex: Int, toIndex: Int) {
    moveMouseToColumnHeader(fromIndex)
    robot.pressMouse(MouseButton.LEFT_BUTTON)
    for (i in (fromIndex + 1)..toIndex) {
        moveMouseToColumnHeader(i)
    }
    robot.releaseMouse(MouseButton.LEFT_BUTTON)
}

fun JTableFixture.getColumnNames(): List<String> =
        tableHeader().component().columnModel.columns.toList()
                .map { it.headerValue.toString() }.toList()



