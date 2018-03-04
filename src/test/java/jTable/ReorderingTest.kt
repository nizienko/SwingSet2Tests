package jTable

import engine.extensions.dragColumn
import engine.extensions.getColumnNames
import engine.extensions.moveMouseToColumnHeader
import org.fest.swing.core.MouseButton.LEFT_BUTTON
import org.junit.Test
import swingSet2.SwingSet2
import java.awt.Point
import kotlin.test.assertEquals

class ReorderingTest : JTableTestSuite() {

    @Test
    fun checkReorderingOnAdjacentColumns(): Unit = with(SwingSet2.jTablePanel) {
        val columnsBefore = table.getColumnNames()

        reorderingAllowedCheckBox.check()
        table.dragColumn(fromIndex = 0, toIndex = 1)

        val columnsAfter = table.getColumnNames()

        assertEquals(columnsBefore[0], columnsAfter[1])
        assertEquals(columnsBefore[1], columnsAfter[0])
    }

    @Test
    fun checkReorderingOnSeparatedColumns(): Unit = with(SwingSet2.jTablePanel) {
        val columnsBefore = table.getColumnNames()

        reorderingAllowedCheckBox.check()
        table.dragColumn(fromIndex = 0, toIndex = 4)

        val columnsAfter = table.getColumnNames()

        assertEquals(columnsBefore[0], columnsAfter[4])
        assertEquals(columnsBefore[1], columnsAfter[0])
        assertEquals(columnsBefore[2], columnsAfter[1])
        assertEquals(columnsBefore[3], columnsAfter[2])
        assertEquals(columnsBefore[4], columnsAfter[3])
    }

    @Test
    fun checkReorderingOff(): Unit = with(SwingSet2.jTablePanel) {
        val columnsBefore = table.getColumnNames()

        reorderingAllowedCheckBox.uncheck()
        table.dragColumn(fromIndex = 0, toIndex = 1)

        val columnsAfter = table.getColumnNames()

        assertEquals(columnsBefore[0], columnsAfter[0])
        assertEquals(columnsBefore[1], columnsAfter[1])
    }

    @Test
    fun checkReorderingOnMovingOutOfApplication(): Unit = with(SwingSet2.jTablePanel) {
        val columnsBefore = table.getColumnNames()

        reorderingAllowedCheckBox.check()
        with(table) {
            moveMouseToColumnHeader(1)
            with(robot) {
                pressMouse(LEFT_BUTTON)
                moveMouse(Point(0, 0))
                releaseMouse(LEFT_BUTTON)
            }
        }

        val columnsAfter = table.getColumnNames()

        assertEquals(columnsBefore[1], columnsAfter[0])
        assertEquals(columnsBefore[0], columnsAfter[1])
        assertEquals(columnsBefore[5], columnsAfter[5])
    }
}