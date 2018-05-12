package jTable.columnAutoresizeMode

import configuration.app
import engine.helpers.*
import jTable.JTableTestSuite
import org.amshove.kluent.shouldEqual
import org.fest.swing.core.MouseButton
import org.junit.BeforeClass
import org.junit.Test
import java.awt.Point


class ColumnAutoresizeModeSubsequentColumnsTest : JTableTestSuite() {
    companion object {
        @JvmStatic
        @BeforeClass
        fun switchAutoresizeOff() {
            app.pageObject.jTablePanel.autoresizeModeComboBox.selectItem("Subsequent columns")
        }
    }

    @Test
    fun allColumnsResized(): Unit = with(app.pageObject.jTablePanel) {
        val columnWidthsBefore = table.getColumnsWidth()
        val columnsRatios = table.columnsSizeRatio()

        with(table) {
            val targetPoint = getColumnHeaderPoint(0, ColumnHeaderPart.RIGHT).let { Point(it.x + 50, it.y) }
            moveMouseToColumnHeader(0, ColumnHeaderPart.RIGHT)
            with(robot) {
                pressingMouse(MouseButton.LEFT_BUTTON) {
                    moveMouse(targetPoint)
                }
            }
        }

        val columnWidthsAfter = table.getColumnsWidth()

        columnWidthsAfter[0] shouldEqual columnWidthsBefore[0] + 50
        (1..5).forEach {
            columnWidthsAfter[it] shouldEqual columnWidthsBefore[it] - 10.applyRatio(columnsRatios[it])
        }
    }

    @Test
    fun allColumnsAtRightSideResized(): Unit = with(app.pageObject.jTablePanel) {
        val columnWidthsBefore = table.getColumnsWidth()
        val columnsRatios = table.columnsSizeRatio()

        with(table) {
            val targetPoint = getColumnHeaderPoint(2, ColumnHeaderPart.RIGHT).let { Point(it.x - 21, it.y) }
            moveMouseToColumnHeader(2, ColumnHeaderPart.RIGHT)
            with(robot) {
                pressingMouse(MouseButton.LEFT_BUTTON) {
                    moveMouse(targetPoint)
                }
            }
        }

        val columnWidthsAfter = table.getColumnsWidth()

        (0..1).forEach {
            columnWidthsAfter[it] shouldEqual columnWidthsBefore[it]
        }
        columnWidthsAfter[2] shouldEqual columnWidthsBefore[2] - 21
        (3..5).forEach {
            columnWidthsAfter[it] shouldEqual columnWidthsBefore[it] + 7.applyRatio(columnsRatios[it])
        }
    }

    @Test
    fun lastTwoColumnsSideResized(): Unit = with(app.pageObject.jTablePanel) {
        val columnWidthsBefore = table.getColumnsWidth()

        with(table) {
            val targetPoint = getColumnHeaderPoint(5, ColumnHeaderPart.LEFT).let { Point(it.x - 50, it.y) }
            moveMouseToColumnHeader(5, ColumnHeaderPart.LEFT)
            with(robot) {
                pressingMouse(MouseButton.LEFT_BUTTON) {
                    moveMouse(targetPoint)
                }
            }
        }

        val columnWidthsAfter = table.getColumnsWidth()

        (0..3).forEach {
            columnWidthsAfter[it] shouldEqual columnWidthsBefore[it]
        }
        columnWidthsAfter[4] shouldEqual columnWidthsBefore[4] - 50
        columnWidthsAfter[5] shouldEqual columnWidthsBefore[5] + 50
    }
}