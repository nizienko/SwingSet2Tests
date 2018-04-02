package jTable.columnReordering

import engine.extensions.*
import jTable.JTableTestSuite
import org.fest.swing.core.MouseButton.LEFT_BUTTON
import org.junit.Test
import swingSet2.SwingSet2
import java.awt.Point

class ReorderingTest : JTableTestSuite() {

    @Test
    fun checkReorderingAdjacentColumns(): Unit = with(SwingSet2.jTablePanel) {
        val columnNamesBefore = table.getColumnNames()

        reorderingAllowedCheckBox.check()
        with(table) {
            dragColumnToAnother(fromIndex = 0, toIndex = 1)
            getColumnNames() comparingWith columnNamesBefore shouldHasEqualValuesOn mapOf(
                    1 to 0,
                    0 to 1
            )
        }
    }

    @Test
    fun checkReorderingThroughSeveralColumns(): Unit = with(SwingSet2.jTablePanel) {
        val columnNamesBefore = table.getColumnNames()

        reorderingAllowedCheckBox.check()
        with(table) {
            dragColumnToAnother(fromIndex = 0, toIndex = 4)
            getColumnNames() comparingWith columnNamesBefore shouldHasEqualValuesOn mapOf(
                    4 to 0,
                    0 to 1,
                    1 to 2,
                    2 to 3,
                    3 to 4
            )
        }
    }

    @Test
    fun checkReorderingIsOff(): Unit = with(SwingSet2.jTablePanel) {
        val columnNamesBefore = table.getColumnNames()

        reorderingAllowedCheckBox.uncheck()
        with(table) {
            dragColumnToAnother(fromIndex = 0, toIndex = 1)
            getColumnNames() comparingWith columnNamesBefore shouldHasEqualValuesOn mapOf(
                    0 to 0,
                    1 to 1
            )
        }
    }

    @Test
    fun checkReorderingWhenMovingOutOfFrame(): Unit = with(SwingSet2.jTablePanel) {
        val columnNamesBefore = table.getColumnNames()

        reorderingAllowedCheckBox.check()
        with(table) {
            moveMouseToColumnHeader(3)
            robot.pressingMouse(LEFT_BUTTON) {
                moveMouseGradually(
                        from = getColumnHeaderPoint(3),
                        to = getColumnHeaderPoint(0).let { Point(it.x - 300, it.y + 100) },
                        skippedPixels = 100
                )
            }
            getColumnNames() comparingWith columnNamesBefore shouldHasEqualValuesOn mapOf(
                    0 to 3,
                    1 to 0,
                    2 to 1,
                    4 to 4
            )
        }
    }

    @Test
    fun checkBoundaryValueDoNotReorder(): Unit = with(SwingSet2.jTablePanel) {
        val columnNamesBefore = table.getColumnNames()

        reorderingAllowedCheckBox.check()

        with(table) {
            moveMouseToColumnHeader(3)
            val shiftValue = component().tableHeader.getHeaderRect(3).width.let {
                it / 2 + it % 2
            }
            val middlePoint = getColumnHeaderPoint(3).let { Point(it.x + shiftValue - 5, it.y) }
            robot.pressingMouse(LEFT_BUTTON) {
                moveMouse(middlePoint)
                moveMouseGradually(
                        from = middlePoint,
                        to = middlePoint.let { Point(it.x + 5, it.y) }
                )
            }
            getColumnNames() comparingWith columnNamesBefore shouldHasEqualValuesOn mapOf(
                    3 to 3,
                    4 to 4
            )
        }
    }

    @Test
    fun checkBoundaryValueReorder(): Unit = with(SwingSet2.jTablePanel) {
        val columnNamesBefore = table.getColumnNames()

        reorderingAllowedCheckBox.check()
        with(table) {
            val shiftValue = component().tableHeader.getHeaderRect(3).width.let {
                it / 2 + it % 2 + 1
            }
            moveMouseToColumnHeader(3)

            robot.pressingMouse(LEFT_BUTTON) {
                val middlePoint = getColumnHeaderPoint(3).let { Point(it.x + shiftValue - 5, it.y) }
                moveMouse(middlePoint)
                moveMouseGradually(
                        from = middlePoint,
                        to = middlePoint.let { Point(it.x + 5, it.y) }
                )
            }
            getColumnNames() comparingWith columnNamesBefore shouldHasEqualValuesOn mapOf(
                    3 to 4,
                    4 to 3
            )
        }
    }
}