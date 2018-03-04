package jTable

import engine.extensions.*
import org.amshove.kluent.shouldEqual
import org.fest.swing.core.MouseButton.LEFT_BUTTON
import org.junit.Test
import swingSet2.SwingSet2
import java.awt.Point

class ReorderingTest : JTableTestSuite() {

    @Test
    fun checkReorderingAdjacentColumns(): Unit = with(SwingSet2.jTablePanel) {
        val columnsBefore = table.getColumnNames()

        reorderingAllowedCheckBox.check()

        table.dragColumnThroughColumns(fromIndex = 0, toIndex = 1)

        val columnsAfter = table.getColumnNames()

        columnsBefore[0] shouldEqual columnsAfter[1]
        columnsBefore[1] shouldEqual columnsAfter[0]
    }

    @Test
    fun checkReorderingThroughSeveralColumns(): Unit = with(SwingSet2.jTablePanel) {
        val columnsBefore = table.getColumnNames()

        reorderingAllowedCheckBox.check()
        table.dragColumnThroughColumns(fromIndex = 0, toIndex = 4)

        val columnsAfter = table.getColumnNames()

        columnsBefore[0] shouldEqual columnsAfter[4]
        columnsBefore[1] shouldEqual columnsAfter[0]
        columnsBefore[2] shouldEqual columnsAfter[1]
        columnsBefore[3] shouldEqual columnsAfter[2]
        columnsBefore[4] shouldEqual columnsAfter[3]
    }

    @Test
    fun checkReorderingIsOff(): Unit = with(SwingSet2.jTablePanel) {
        val columnsBefore = table.getColumnNames()

        reorderingAllowedCheckBox.uncheck()

        table.dragColumnThroughColumns(fromIndex = 0, toIndex = 1)

        val columnsAfter = table.getColumnNames()

        columnsBefore[0] shouldEqual columnsAfter[0]
        columnsBefore[1] shouldEqual columnsAfter[1]
    }

    @Test
    fun checkReorderingWhenMovingOutOfFrame(): Unit = with(SwingSet2.jTablePanel) {
        val columnsBefore = table.getColumnNames()

        reorderingAllowedCheckBox.check()

        with(table) {
            moveMouseToColumnHeader(3)
            robot.pressingMouse(LEFT_BUTTON) {
                moveMouseGradually(
                        from = getColumnHeaderPoint(3),
                        to = getColumnHeaderPoint(0).let { Point(it.x - 300, it.y + 100) },
                        step = 100
                )
            }
        }

        val columnsAfter = table.getColumnNames()

        columnsBefore[3] shouldEqual columnsAfter[0]
        columnsBefore[0] shouldEqual columnsAfter[1]
        columnsBefore[1] shouldEqual columnsAfter[2]
        columnsBefore[4] shouldEqual columnsAfter[4]
    }

    @Test
    fun checkBoundaryValueDoNotReorder(): Unit = with(SwingSet2.jTablePanel) {
        val columnsBefore = table.getColumnNames()
        val columnN = 3

        reorderingAllowedCheckBox.check()

        with(table) {
            moveMouseToColumnHeader(columnN)
            val shiftValue = component().tableHeader.getHeaderRect(columnN).width.let {
                it / 2 + it % 2
            }
            val middlePoint = getColumnHeaderPoint(columnN).let { Point(it.x + shiftValue - 5, it.y) }
            robot.pressingMouse(LEFT_BUTTON) {
                moveMouse(middlePoint)
                moveMouseGradually(
                        from = middlePoint,
                        to = middlePoint.let { Point(it.x + 5, it.y) }
                )
            }
        }

        val columnsAfter = table.getColumnNames()

        columnsBefore[columnN] shouldEqual columnsAfter[columnN]
        columnsBefore[columnN + 1] shouldEqual columnsAfter[columnN + 1]
    }

    @Test
    fun checkBoundaryValueReorder(): Unit = with(SwingSet2.jTablePanel) {
        val columnsBefore = table.getColumnNames()
        val columnN = 3

        reorderingAllowedCheckBox.check()
        with(table) {
            val shiftValue = component().tableHeader.getHeaderRect(columnN).width.let {
                it / 2 + it % 2 + 1
            }
            moveMouseToColumnHeader(columnN)

            robot.pressingMouse(LEFT_BUTTON) {
                val middlePoint = getColumnHeaderPoint(columnN).let { Point(it.x + shiftValue - 5, it.y) }
                moveMouse(middlePoint)
                moveMouseGradually(
                        from = middlePoint,
                        to = middlePoint.let { Point(it.x + 5, it.y) },
                        step = 1
                )
            }
        }

        val columnsAfter = table.getColumnNames()

        columnsBefore[columnN] shouldEqual columnsAfter[columnN + 1]
        columnsBefore[columnN + 1] shouldEqual columnsAfter[columnN]
    }
}