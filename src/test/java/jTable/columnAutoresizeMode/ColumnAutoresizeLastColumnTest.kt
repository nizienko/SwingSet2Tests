package jTable.columnAutoresizeMode

import configuration.app
import engine.helpers.*
import jTable.JTableTestSuite
import org.amshove.kluent.shouldEqual
import org.fest.swing.core.MouseButton
import org.junit.BeforeClass
import org.junit.Test
import java.awt.Point

class ColumnAutoresizeLastColumnTest: JTableTestSuite() {
    companion object {
        @JvmStatic
        @BeforeClass
        fun switchAutoresizeOff() {
            app.pageObject.jTablePanel.autoresizeModeComboBox.selectItem("Last column")
        }
    }

    @Test
    fun lastColumnsResized(): Unit = with(app.pageObject.jTablePanel) {
        val columnWidthsBefore = table.getColumnsWidth()

        with(table) {
            val targetPoint = getColumnHeaderPoint(0, ColumnHeaderPart.RIGHT).let { Point(it.x + 40, it.y) }
            moveMouseToColumnHeader(0, ColumnHeaderPart.RIGHT)
            with(robot) {
                pressingMouse(MouseButton.LEFT_BUTTON) {
                    moveMouse(targetPoint)
                }
            }
        }

        val columnWidthsAfter = table.getColumnsWidth()

        columnWidthsAfter[0] shouldEqual columnWidthsBefore[0] + 40
        columnWidthsAfter[5] shouldEqual columnWidthsBefore[5] - 40
        (1..4).forEach {
            columnWidthsAfter[it] shouldEqual columnWidthsBefore[it]
        }
    }
}