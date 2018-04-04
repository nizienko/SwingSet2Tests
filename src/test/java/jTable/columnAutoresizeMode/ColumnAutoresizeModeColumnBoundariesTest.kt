package jTable.columnAutoresizeMode

import engine.helpers.*
import jTable.JTableTestSuite
import org.amshove.kluent.shouldEqual
import org.fest.swing.core.MouseButton
import org.junit.BeforeClass
import org.junit.Test
import swingSet2.SwingSet2
import java.awt.Point

class ColumnAutoresizeModeColumnBoundariesTest : JTableTestSuite() {
    companion object {
        @JvmStatic
        @BeforeClass
        fun switchAutoresizeOff() {
            SwingSet2.jTablePanel.autoresizeModeComboBox.selectItem("Column boundaries")
        }
    }

    @Test
    fun onlyTwoColumnsResized(): Unit = with(SwingSet2.jTablePanel) {
        val columnWidthsBefore = table.getColumnsWidth()

        with(table) {
            val targetPoint = getColumnHeaderPoint(0, ColumnHeaderPart.RIGHT).let { Point(it.x + 20, it.y) }
            moveMouseToColumnHeader(0, ColumnHeaderPart.RIGHT)
            with(robot) {
                pressingMouse(MouseButton.LEFT_BUTTON) {
                    moveMouse(targetPoint)
                }
            }
        }

        val columnWidthsAfter = table.getColumnsWidth()

        columnWidthsAfter[0] shouldEqual columnWidthsBefore[0] + 20
        columnWidthsAfter[1] shouldEqual columnWidthsBefore[1] - 20
        (2..5).forEach {
            columnWidthsAfter[it] shouldEqual columnWidthsBefore[it]
        }
    }

    @Test
    fun minimizeLeftColumnWithOutOffAppMoving(): Unit = with(SwingSet2.jTablePanel) {
        val columnWidthsBefore = table.getColumnsWidth()

        with(table) {
            val fromPoint = getColumnHeaderPoint(4, ColumnHeaderPart.RIGHT)
            val toPoint = fromPoint.let { Point(it.x - 800, it.y + 200) }
            with(robot) {
                moveMouse(fromPoint)
                pressingMouse(MouseButton.LEFT_BUTTON) {
                    moveMouseGradually(
                            from = fromPoint,
                            to = toPoint,
                            skippedPixels = 20)
                }
            }
        }
        val columnWidthsAfter = table.getColumnsWidth()

        columnWidthsAfter[4] shouldEqual 15
        columnWidthsAfter[5] shouldEqual columnWidthsBefore[5] + columnWidthsBefore[4] - 15
        (0..3).forEach {
            columnWidthsAfter[it] shouldEqual columnWidthsBefore[it]
        }
    }

    @Test
    fun minimizeRightColumnWithOutOffAppMoving(): Unit = with(SwingSet2.jTablePanel) {
        val columnWidthsBefore = table.getColumnsWidth()

        with(table) {
            val fromPoint = getColumnHeaderPoint(4, ColumnHeaderPart.RIGHT)
            val toPoint = fromPoint.let { Point(it.x + 200, it.y + 200) }
            with(robot) {
                moveMouse(fromPoint)
                pressingMouse(MouseButton.LEFT_BUTTON) {
                    moveMouseGradually(
                            from = fromPoint,
                            to = toPoint,
                            skippedPixels = 20)
                }
            }
        }
        val columnWidthsAfter = table.getColumnsWidth()

        columnWidthsAfter[4] shouldEqual columnWidthsBefore[5] + columnWidthsBefore[4] - 15
        columnWidthsAfter[5] shouldEqual 15
        (0..3).forEach {
            columnWidthsAfter[it] shouldEqual columnWidthsBefore[it]
        }
    }
}