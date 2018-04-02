package jTable.columnAutoresizeMode

import engine.extensions.ColumnHeaderPart.RIGHT
import engine.extensions.getColumnHeaderPoint
import engine.extensions.getColumnsWidth
import engine.extensions.moveMouseToColumnHeader
import engine.extensions.pressingMouse
import jTable.JTableTestSuite
import org.amshove.kluent.shouldEqual
import org.fest.swing.core.MouseButton.LEFT_BUTTON
import org.junit.BeforeClass
import org.junit.Test
import swingSet2.SwingSet2
import java.awt.Point

class ColumnAutoresizeModeOffTest : JTableTestSuite() {
    companion object {
        @JvmStatic
        @BeforeClass
        fun switchAutoresizeOff() {
            SwingSet2.jTablePanel.autoresizeModeComboBox.selectItem("Off")
        }
    }

    @Test
    fun onlyOneColumnsResized(): Unit = with(SwingSet2.jTablePanel) {
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
}