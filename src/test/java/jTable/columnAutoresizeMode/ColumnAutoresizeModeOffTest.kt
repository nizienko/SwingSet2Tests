package jTable.columnAutoresizeMode

import configuration.app
import engine.helpers.*
import engine.helpers.ColumnHeaderPart.RIGHT
import jTable.JTableTestSuite
import org.amshove.kluent.shouldEqual
import org.fest.swing.core.MouseButton.LEFT_BUTTON
import org.junit.BeforeClass
import org.junit.Test
import java.awt.Point

class ColumnAutoresizeModeOffTest : JTableTestSuite() {
    companion object {
        @JvmStatic
        @BeforeClass
        fun switchAutoresizeOff() {
            app.pageObject.jTablePanel.autoresizeModeComboBox.selectItem("Off")
        }
    }

    @Test
    fun onlyOneColumnsResized(): Unit = with(app.pageObject.jTablePanel) {
        val columnWidthsBefore = table.getColumnsWidth()

        with(table) {
            val targetPoint = getColumnHeaderPoint(0, RIGHT).let { Point(it.x + 20, it.y) }
            moveMouseToColumnHeader(0, RIGHT)
            with(robot) {
                pressingMouse(LEFT_BUTTON) {
                    moveMouse(targetPoint)
                }
            }
        }
        val columnWidthsAfter = table.getColumnsWidth()

        columnWidthsAfter[0] shouldEqual columnWidthsBefore[0] + 20
        (1..5).forEach {
            columnWidthsAfter[it] shouldEqual columnWidthsBefore[it]
        }
    }

    @Test
    fun minimizeColumnWithOutOffAppMoving(): Unit = with(app.pageObject.jTablePanel) {
        val columnWidthsBefore = table.getColumnsWidth()

        with(table) {
            val fromPoint = getColumnHeaderPoint(4, RIGHT)
            val toPoint = fromPoint.let { Point(it.x - 600, it.y + 200) }
            with(robot) {
                moveMouse(fromPoint)
                pressingMouse(LEFT_BUTTON) {
                    moveMouseGradually(
                            from = fromPoint,
                            to = toPoint,
                            skippedPixels = 20)
                }
            }
        }
        val columnWidthsAfter = table.getColumnsWidth()

        columnWidthsAfter[4] shouldEqual 15
        (0..3).forEach {
            columnWidthsAfter[it] shouldEqual columnWidthsBefore[it]
        }
        columnWidthsAfter[5] shouldEqual columnWidthsBefore[5]
    }
}