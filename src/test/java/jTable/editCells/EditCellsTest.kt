package jTable.editCells

import engine.helpers.clickCells
import engine.helpers.pressingKey
import jTable.JTableTestSuite
import org.amshove.kluent.shouldEqual
import org.amshove.kluent.shouldNotEqual
import org.fest.swing.data.TableCell
import org.junit.After
import org.junit.Test
import swingSet2.SwingSet2
import java.awt.event.KeyEvent.*
import java.math.BigDecimal

class EditCellsTest: JTableTestSuite() {

    @After
    fun pressEscape() = SwingSet2.jTablePanel.table.robot.pressAndReleaseKey(VK_ESCAPE)

    @Test
    fun editText() : Unit = with(SwingSet2.jTablePanel) {
        table.cell(TableCell.row(1).column(0)).doubleClick()
        with(table.robot) {
            pressingKey(VK_CONTROL) {
                pressAndReleaseKey(VK_A)
            }
            enterText("Test text")
            pressAndReleaseKey(VK_ENTER)
        }
        table.cell(TableCell.row(1).column(0)).value() shouldEqual "Test text"
    }

    @Test
    fun clearText(): Unit = with(SwingSet2.jTablePanel) {
        table.cell(TableCell.row(2).column(0)).doubleClick()
        with(table.robot) {
            pressingKey(VK_CONTROL) {
                pressAndReleaseKey(VK_A)
            }
            pressAndReleaseKey(VK_BACK_SPACE)
            pressAndReleaseKey(VK_ENTER)
        }
        table.cell(TableCell.row(2).column(0)).value().length shouldEqual 0
    }

    @Test
    fun cantInsertTextInNumberFormatCell(): Unit = with(SwingSet2.jTablePanel) {
        table.cell(TableCell.row(10).column(4)).doubleClick()
        with(table.robot) {
            pressingKey(VK_CONTROL) {
                pressAndReleaseKey(VK_A)
            }
            enterText("56.2e")
            pressAndReleaseKey(VK_ENTER)
        }
        table.cell(TableCell.row(10).column(4)).value() shouldNotEqual "56.2e"
    }

    @Test
    fun insertNumberInNumberFormatCell(): Unit = with(SwingSet2.jTablePanel) {
        table.cell(TableCell.row(10).column(4)).doubleClick()
        with(table.robot) {
            pressingKey(VK_CONTROL) {
                pressAndReleaseKey(VK_A)
            }
            enterText("0.123")
            pressAndReleaseKey(VK_ENTER)
        }
        table.cell(TableCell.row(10).column(4)).value().replace(",", ".")
                .toBigDecimal() shouldEqual BigDecimal("0.123")
    }


    @Test
    fun selectColor(): Unit = with(SwingSet2.jTablePanel) {
        table.cell(TableCell.row(5).column(2)).background().target().toString() shouldEqual "Black"
        table.clickCells {
            clickCell(5, 2)
        }
        with(table.robot) {
            repeat(5) { pressAndReleaseKey(VK_DOWN) }
            pressAndReleaseKey(VK_ENTER)
        }
        table.cell(TableCell.row(5).column(2)).background().target().toString() shouldEqual "Cyber Green"
    }
}