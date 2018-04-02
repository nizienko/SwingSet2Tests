package jTable.columnAutoresizeMode

import engine.extensions.*
import jTable.JTableTestSuite
import org.amshove.kluent.*
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

        columnWidthsAfter[0] shouldEqual columnWidthsBefore[0] + 40
        (1..3).forEach {
            columnWidthsAfter[it] shouldEqual columnWidthsBefore[it]
        }
        (4..5).forEach {
            columnWidthsAfter[it] shouldEqual columnWidthsBefore[it] - 20
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

        columnWidthsAfter[1] shouldBeGreaterOrEqualTo columnWidthsBefore[1] + 30
        (2..5).forEach {
            columnWidthsAfter[it] shouldBeLessThan columnWidthsBefore[it] - 3
        }
        columnWidthsAfter[0] shouldBeLessThan columnWidthsBefore[0] - 3
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

        columnWidthsAfter[0] shouldEqual columnWidthsBefore[0] - 40
        (1..3).forEach {
            columnWidthsAfter[it] shouldEqual columnWidthsBefore[it]
        }
        (4..5).forEach {
            columnWidthsAfter[it] shouldEqual columnWidthsBefore[it] + 20
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

        columnWidthsAfter[1] shouldBeLessOrEqualTo columnWidthsBefore[1] - 30
        (2..5).forEach {
            columnWidthsAfter[it] shouldBeGreaterThan columnWidthsBefore[it] + 5
        }
        columnWidthsAfter[0] shouldBeGreaterThan columnWidthsBefore[0] + 5
    }
}