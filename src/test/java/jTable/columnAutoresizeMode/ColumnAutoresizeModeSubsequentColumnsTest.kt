package jTable.columnAutoresizeMode

import engine.extensions.ColumnHeaderPart
import engine.extensions.getColumnHeaderPoint
import engine.extensions.moveMouseToColumnHeader
import engine.extensions.pressingMouse
import jTable.JTableTestSuite
import org.amshove.kluent.shouldEqual
import org.fest.swing.core.MouseButton
import org.junit.BeforeClass
import org.junit.Test
import swingSet2.SwingSet2
import java.awt.Point
import java.math.BigDecimal
import java.math.RoundingMode

class ColumnAutoresizeModeSubsequentColumnsTest : JTableTestSuite() {
    companion object {
        @JvmStatic
        @BeforeClass
        fun switchAutoresizeOff() {
            SwingSet2.jTablePanel.autoresizeModeComboBox.selectItem("Subsequent columns")
        }
    }

    @Test
    fun allColumnsResized(): Unit = with(SwingSet2.jTablePanel) {
        val columnWidthsBefore = (0..5).map {
            table.tableHeader().component().getHeaderRect(it).width
        }
        val allColumnsSize = columnWidthsBefore.sum()

        val columnsRatios = (0..5).map {
            calculateColumnSizeRatio(allColumnsSize, 6, columnWidthsBefore[it])
        }
        println(columnsRatios)

        with(table) {
            val targetPoint = getColumnHeaderPoint(0, ColumnHeaderPart.RIGHT).let { Point(it.x + 50, it.y) }
            moveMouseToColumnHeader(0, ColumnHeaderPart.RIGHT)
            with(robot) {
                pressingMouse(MouseButton.LEFT_BUTTON) {
                    moveMouse(targetPoint)
                }
            }
        }

        val columnWidthsAfter = (0..5).map {
            table.tableHeader().component().getHeaderRect(it).width
        }

        columnWidthsAfter[0] shouldEqual columnWidthsBefore[0] + 50
        (1..5).forEach {
            columnWidthsAfter[it] shouldEqual columnWidthsBefore[it] - BigDecimal(10).multiply(columnsRatios[it]).setScale(0, RoundingMode.HALF_UP).toInt()
        }
    }

    private fun calculateColumnSizeRatio(columnsSize: Int, columnsCount: Int, currentColumnSize: Int): BigDecimal {
        return BigDecimal(currentColumnSize).divide((BigDecimal(columnsSize)
                .divide(BigDecimal(columnsCount), 5, RoundingMode.HALF_UP)), 5, RoundingMode.HALF_UP)
    }

    @Test
    fun allColumnsAtRightSideResized(): Unit = with(SwingSet2.jTablePanel) {
        val columnWidthsBefore = (0..5).map {
            table.tableHeader().component().getHeaderRect(it).width
        }

        with(table) {
            val targetPoint = getColumnHeaderPoint(2, ColumnHeaderPart.RIGHT).let { Point(it.x - 21, it.y) }
            moveMouseToColumnHeader(2, ColumnHeaderPart.RIGHT)
            with(robot) {
                pressingMouse(MouseButton.LEFT_BUTTON) {
                    moveMouse(targetPoint)
                }
            }
        }

        val columnWidthsAfter = (0..5).map {
            table.tableHeader().component().getHeaderRect(it).width
        }
        (0..1).forEach {
            columnWidthsAfter[it] shouldEqual columnWidthsBefore[it]
        }
        columnWidthsAfter[2] shouldEqual columnWidthsBefore[2] - 21
        (3..5).forEach {
            columnWidthsAfter[it] shouldEqual columnWidthsBefore[it] + 7
        }
    }

    @Test
    fun lastTwoColumnsSideResized(): Unit = with(SwingSet2.jTablePanel) {
        val columnWidthsBefore = (0..5).map {
            table.tableHeader().component().getHeaderRect(it).width
        }

        with(table) {
            val targetPoint = getColumnHeaderPoint(5, ColumnHeaderPart.LEFT).let { Point(it.x - 50, it.y) }
            moveMouseToColumnHeader(5, ColumnHeaderPart.LEFT)
            with(robot) {
                pressingMouse(MouseButton.LEFT_BUTTON) {
                    moveMouse(targetPoint)
                }
            }
        }

        val columnWidthsAfter = (0..5).map {
            table.tableHeader().component().getHeaderRect(it).width
        }
        (0..3).forEach {
            columnWidthsAfter[it] shouldEqual columnWidthsBefore[it]
        }
        columnWidthsAfter[4] shouldEqual columnWidthsBefore[4] - 50
        columnWidthsAfter[5] shouldEqual columnWidthsBefore[5] + 50

    }
}