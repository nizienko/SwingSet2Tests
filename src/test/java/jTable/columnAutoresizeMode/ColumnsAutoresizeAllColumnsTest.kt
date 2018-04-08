package jTable.columnAutoresizeMode

import engine.helpers.*
import jTable.JTableTestSuite
import org.fest.swing.core.MouseButton
import org.junit.BeforeClass
import org.junit.Test
import swingSet2.SwingSet2
import java.awt.Point

class ColumnsAutoresizeAllColumnsTest : JTableTestSuite() {
    companion object {
        @JvmStatic
        @BeforeClass
        fun switchAutoresizeOff() {
            SwingSet2.jTablePanel.autoresizeModeComboBox.selectItem("All columns")
        }
    }

    @Test
    fun lastTwoColumnsResizedWithColumnEditingSlowly(): Unit = with(SwingSet2.jTablePanel) {
        val columnWidthsBefore = table.getColumnsWidth()

        with(table) {
            val targetPoint = getColumnHeaderPoint(0, ColumnHeaderPart.RIGHT).let { Point(it.x + 40, it.y) }
            moveMouseToColumnHeader(0, ColumnHeaderPart.RIGHT)
            with(robot) {
                pressingMouse(MouseButton.LEFT_BUTTON) {
                    moveMouseGradually(
                            from = getColumnHeaderPoint(0, ColumnHeaderPart.RIGHT),
                            to = targetPoint
                    )
                }
            }
        }
        val columnWidthsAfter = table.getColumnsWidth()

        columnWidthsAfter[0] shouldBeBetween columnWidthsBefore[0] + 35 and columnWidthsBefore[0] + 40
        (1..3).forEach {
            columnWidthsAfter[it] shouldBeBetween columnWidthsBefore[it] - 3 and columnWidthsBefore[it]
        }
        (4..5).forEach {
            columnWidthsAfter[it] shouldBeBetween columnWidthsBefore[it] - 20 and columnWidthsBefore[it] - 15
        }
    }

    @Test
    fun allColumnsResizedWithColumnEditingQuickly(): Unit = with(SwingSet2.jTablePanel) {
        val columnWidthsBefore = table.getColumnsWidth()

        with(table) {
            val targetPoint = getColumnHeaderPoint(1, ColumnHeaderPart.RIGHT).let { Point(it.x + 40, it.y) }
            moveMouseToColumnHeader(1, ColumnHeaderPart.RIGHT)
            with(robot) {
                pressingMouse(MouseButton.LEFT_BUTTON) {
                    moveMouseGradually(
                            from = getColumnHeaderPoint(1, ColumnHeaderPart.RIGHT),
                            to = targetPoint,
                            skippedPixels = 10
                    )
                }
            }
        }
        val columnWidthsAfter = table.getColumnsWidth()

        columnWidthsAfter[1] shouldBeBetween columnWidthsBefore[1] + 30 and columnWidthsBefore[1] + 40
        (2..5).forEach {
            columnWidthsAfter[it] shouldBeBetween columnWidthsBefore[it] - 10 and columnWidthsBefore[it] - 2
        }
        columnWidthsAfter[0] shouldBeBetween columnWidthsBefore[0] - 10 and columnWidthsBefore[0] - 2
    }

    @Test
    fun lastTwoColumnsResizedWithColumnEditingSlowlyDecreasing(): Unit = with(SwingSet2.jTablePanel) {
        val columnWidthsBefore = table.getColumnsWidth()

        with(table) {
            val targetPoint = getColumnHeaderPoint(0, ColumnHeaderPart.RIGHT).let { Point(it.x - 40, it.y) }
            moveMouseToColumnHeader(0, ColumnHeaderPart.RIGHT)
            with(robot) {
                pressingMouse(MouseButton.LEFT_BUTTON) {
                    moveMouseGradually(
                            from = getColumnHeaderPoint(0, ColumnHeaderPart.RIGHT),
                            to = targetPoint
                    )
                }
            }
        }

        val columnWidthsAfter = table.getColumnsWidth()

        columnWidthsAfter[0] shouldBeBetween columnWidthsBefore[0] - 40 and columnWidthsBefore[0] - 35
        (1..3).forEach {
            columnWidthsAfter[it] shouldBeBetween columnWidthsBefore[it] and columnWidthsBefore[it] + 5
        }
        (4..5).forEach {
            columnWidthsAfter[it] shouldBeBetween columnWidthsBefore[it] + 15 and columnWidthsBefore[it] + 25
        }
    }

    @Test
    fun allColumnsResizedWithColumnEditingQuicklyDecreasing(): Unit = with(SwingSet2.jTablePanel) {
        val columnWidthsBefore = table.getColumnsWidth()

        with(table) {
            val targetPoint = getColumnHeaderPoint(1, ColumnHeaderPart.RIGHT).let { Point(it.x - 40, it.y) }
            moveMouseToColumnHeader(1, ColumnHeaderPart.RIGHT)
            with(robot) {
                pressingMouse(MouseButton.LEFT_BUTTON) {
                    moveMouseGradually(
                            from = getColumnHeaderPoint(1, ColumnHeaderPart.RIGHT),
                            to = targetPoint,
                            skippedPixels = 10
                    )
                }
            }
        }
        val columnWidthsAfter = table.getColumnsWidth()

        columnWidthsAfter[1] shouldBeBetween columnWidthsBefore[1] - 40 and columnWidthsBefore[1] - 30
        (2..5).forEach {
            columnWidthsAfter[it] shouldBeBetween columnWidthsBefore[it] + 2 and columnWidthsBefore[it] + 10
        }
        columnWidthsAfter[0] shouldBeBetween columnWidthsBefore[0] + 2 and columnWidthsBefore[0] + 10
    }
}