package jTable.cellSelection

import configuration.app
import engine.helpers.checkCellsSelection
import engine.helpers.clickCells
import engine.helpers.dragColumnToAnother
import jTable.JTableTestSuite
import org.fest.swing.data.TableCell
import org.junit.BeforeClass
import org.junit.Test
import java.awt.event.KeyEvent

class CellSelectionSingleModeTest : JTableTestSuite() {

    companion object {
        @JvmStatic
        @BeforeClass
        fun setSingleSelectionMode() {
            app.pageObject.jTablePanel.selectionModeComboBox.selectItem("Single")
        }
    }


    @Test
    fun noSelectedCellsWhenComboBoxesUnchecked(): Unit = with(app.pageObject.jTablePanel) {
        rowSelectionCheckBox.uncheck()
        columnSelectionCheckBox.uncheck()

        table.clickCells {
            clickCell(1, 0)
        }
        table.checkCellsSelection {
            cellIsNotSelected(1, 0)
        }
    }

    @Test
    fun onlyOneColumnSelectedWhenColumnComboBoxChecked(): Unit = with(app.pageObject.jTablePanel) {
        rowSelectionCheckBox.uncheck()
        columnSelectionCheckBox.check()

        table.clickCells {
            clickCell(7, 1)
            pressingControl {
                clickCell(9, 3)
            }
        }
        table.checkCellsSelection {
            columnsShouldNotBeSelected(columns = listOf(1))
            columnsShouldBeSelected(columns = listOf(3))
            rowsShouldNotBeSelected(rows = listOf(9), exceptColumns = listOf(3))
        }
    }

    @Test
    fun onlyOneColumnFromRangeSelectedWhenColumnComboBoxChecked(): Unit = with(app.pageObject.jTablePanel) {
        rowSelectionCheckBox.uncheck()
        columnSelectionCheckBox.check()

        table.clickCells {
            clickCell(7, 1)
            pressingShift {
                clickCell(9, 3)
            }
        }
        table.checkCellsSelection {
            columnsShouldNotBeSelected(columns = listOf(1, 2))
            columnsShouldBeSelected(columns = listOf(3))
            rowsShouldNotBeSelected(rows = listOf(7, 8, 9), exceptColumns = listOf(3))
        }
    }

    @Test
    fun columnUnSelectedWithSecondClickWhenColumnComboBoxChecked(): Unit = with(app.pageObject.jTablePanel) {
        rowSelectionCheckBox.uncheck()
        columnSelectionCheckBox.check()

        table.clickCells {
            clickCell(9, 5)
            pressingControl {
                clickCell(9, 5)
            }
        }
        table.checkCellsSelection {
            columnsShouldNotBeSelected(columns = listOf(5))
        }
    }

    @Test
    fun columnUnSelectedWithSecondClickOnColumn(): Unit = with(app.pageObject.jTablePanel) {
        rowSelectionCheckBox.uncheck()
        columnSelectionCheckBox.check()

        table.clickCells {
            clickCell(1, 3)
            pressingControl {
                clickCell(15, 3)
            }
        }
        table.checkCellsSelection {
            columnsShouldNotBeSelected(columns = listOf(3))
        }
    }

    @Test
    fun reorderingKeepsColumnSelection(): Unit = with(app.pageObject.jTablePanel) {
        rowSelectionCheckBox.uncheck()
        columnSelectionCheckBox.check()
        reorderingAllowedCheckBox.check()

        table.clickCells {
            clickCell(7, 1)
        }
        table.dragColumnToAnother(fromIndex = 1, toIndex = 4)
        table.checkCellsSelection {
            columnsShouldNotBeSelected(columns = listOf(1))
            columnsShouldBeSelected(columns = listOf(4))
            rowsShouldNotBeSelected(rows = listOf(7), exceptColumns = listOf(4))
        }
    }

    @Test
    fun reorderingFromAnotherColumnKeepsColumnSelection(): Unit = with(app.pageObject.jTablePanel) {
        rowSelectionCheckBox.uncheck()
        columnSelectionCheckBox.check()
        reorderingAllowedCheckBox.check()

        table.clickCells {
            clickCell(7, 1)
        }
        table.dragColumnToAnother(fromIndex = 4, toIndex = 1)
        table.checkCellsSelection {
            columnsShouldNotBeSelected(listOf(4, 1))
            columnsShouldBeSelected(columns = listOf(2))
            rowsShouldNotBeSelected(rows = listOf(7), exceptColumns = listOf(2))
        }
    }


    @Test
    fun sortingKeepsColumnSelection(): Unit = with(app.pageObject.jTablePanel) {
        rowSelectionCheckBox.uncheck()
        columnSelectionCheckBox.check()
        table.tableHeader().clickColumn(0)

        table.clickCells {
            clickCell(7, 1)
        }
        table.tableHeader().clickColumn(1)

        table.checkCellsSelection {
            columnsShouldBeSelected(columns = listOf(1))
        }
    }

    @Test
    fun sortingByAnotherColumnKeepsColumnSelection(): Unit = with(app.pageObject.jTablePanel) {
        rowSelectionCheckBox.uncheck()
        columnSelectionCheckBox.check()
        table.tableHeader().clickColumn(0)

        table.clickCells {
            clickCell(7, 1)
        }
        table.tableHeader().clickColumn(5)

        table.checkCellsSelection {
            columnsShouldBeSelected(columns = listOf(1))
        }
    }

    @Test
    fun onlyOneRowSelectedWhenRowComboBoxChecked(): Unit = with(app.pageObject.jTablePanel) {
        rowSelectionCheckBox.check()
        columnSelectionCheckBox.uncheck()

        table.clickCells {
            clickCell(7, 1)
            pressingControl {
                clickCell(9, 3)
            }
        }
        table.checkCellsSelection {
            rowsShouldNotBeSelected(rows = listOf(7))
            rowsShouldBeSelected(rows = listOf(9))
        }
    }

    @Test
    fun onlyOneRowFromRangeSelectedWhenRowComboBoxChecked(): Unit = with(app.pageObject.jTablePanel) {
        rowSelectionCheckBox.check()
        columnSelectionCheckBox.uncheck()

        table.clickCells {
            clickCell(7, 1)
            pressingShift {
                clickCell(9, 3)
            }
        }
        table.checkCellsSelection {
            rowsShouldNotBeSelected(rows = listOf(7, 8))
            rowsShouldBeSelected(rows = listOf(9))
            columnsShouldNotBeSelected(columns = listOf(3), exceptRows = listOf(9))
        }
    }

    @Test
    fun rowUnSelectedWithSecondClickWhenRowComboBoxChecked(): Unit = with(app.pageObject.jTablePanel) {
        rowSelectionCheckBox.check()
        columnSelectionCheckBox.uncheck()

        table.clickCells {
            clickCell(9, 3)
            pressingControl {
                clickCell(9, 3)
            }
        }
        table.checkCellsSelection {
            rowsShouldNotBeSelected(rows = listOf(9))
        }
    }

    @Test
    fun rowUnSelectedWithSecondClickOnRow(): Unit = with(app.pageObject.jTablePanel) {
        rowSelectionCheckBox.check()
        columnSelectionCheckBox.uncheck()

        table.clickCells {
            clickCell(9, 3)
            pressingControl {
                clickCell(9, 4)
            }
        }
        table.checkCellsSelection {
            rowsShouldNotBeSelected(rows = listOf(9))
        }
    }

    @Test
    fun reorderingKeepsRowSelection(): Unit = with(app.pageObject.jTablePanel) {
        rowSelectionCheckBox.check()
        columnSelectionCheckBox.uncheck()
        reorderingAllowedCheckBox.check()

        table.clickCells {
            clickCell(7, 1)
        }
        table.dragColumnToAnother(fromIndex = 1, toIndex = 4)
        table.checkCellsSelection {
            rowsShouldBeSelected(rows = listOf(7))
        }
    }

    @Test
    fun sortingKeepsRowSelection(): Unit = with(app.pageObject.jTablePanel) {
        rowSelectionCheckBox.check()
        columnSelectionCheckBox.uncheck()
        table.tableHeader().clickColumn(0)
        val firstName = table.cell(TableCell.row(7).column(0)).value()
        table.clickCells {
            clickCell(7, 1)
        }
        table.tableHeader().clickColumn(1)

        val movedCell = table.cell(firstName)

        table.checkCellsSelection {
            rowsShouldBeSelected(rows = listOf(movedCell.row))
            columnsShouldNotBeSelected(columns = listOf(movedCell.column), exceptRows = listOf(movedCell.row))
        }
    }

    @Test
    fun onlyOneCellSelectedWhenBothComboBoxesChecked(): Unit = with(app.pageObject.jTablePanel) {
        rowSelectionCheckBox.check()
        columnSelectionCheckBox.check()

        table.clickCells {
            clickCell(7, 1)
            pressingControl {
                clickCell(9, 3)
            }
        }
        table.checkCellsSelection {
            cellIsNotSelected(7, 1)
            rowsShouldNotBeSelected(rows = listOf(9), exceptColumns = listOf(3))
            columnsShouldNotBeSelected(columns = listOf(3), exceptRows = listOf(9))
        }
    }

    @Test
    fun onlyOneCellFromRangeSelectedWhenBothComboBoxesChecked(): Unit = with(app.pageObject.jTablePanel) {
        rowSelectionCheckBox.check()
        columnSelectionCheckBox.check()

        table.clickCells {
            clickCell(7, 1)
            pressingShift {
                clickCell(9, 3)
            }
        }
        table.checkCellsSelection {
            cellSelected(9, 3)
            columnsShouldNotBeSelected(columns = listOf(1, 2))
            columnsShouldNotBeSelected(columns = listOf(3), exceptRows = listOf(9))
            rowsShouldNotBeSelected(rows = listOf(7, 8))
            rowsShouldNotBeSelected(rows = listOf(9), exceptColumns = listOf(3))
        }
    }

    @Test
    fun cellUnSelectedWithSecondClick(): Unit = with(app.pageObject.jTablePanel) {
        rowSelectionCheckBox.check()
        columnSelectionCheckBox.check()

        table.clickCells {
            clickCell(9, 5)
            pressingControl {
                clickCell(9, 5)
            }
        }
        table.checkCellsSelection {
            cellIsNotSelected(9, 5)
        }
    }


    @Test
    fun reorderingKeepsCellSelection(): Unit = with(app.pageObject.jTablePanel) {
        rowSelectionCheckBox.check()
        columnSelectionCheckBox.check()
        reorderingAllowedCheckBox.check()

        table.clickCells {
            clickCell(7, 1)
        }
        table.dragColumnToAnother(fromIndex = 1, toIndex = 4)
        table.checkCellsSelection {
            cellIsNotSelected(7, 1)
            cellSelected(7, 4)
        }
    }

    @Test
    fun reorderingFromAnotherColumnKeepsCellSelection(): Unit = with(app.pageObject.jTablePanel) {
        rowSelectionCheckBox.check()
        columnSelectionCheckBox.check()
        reorderingAllowedCheckBox.check()

        table.clickCells {
            clickCell(7, 1)
        }
        table.dragColumnToAnother(fromIndex = 4, toIndex = 1)
        table.checkCellsSelection {
            cellIsNotSelected(7, 1)
            cellSelected(7, 2)
        }
    }

    @Test
    fun sortingKeepsCellSelection(): Unit = with(app.pageObject.jTablePanel) {
        rowSelectionCheckBox.check()
        columnSelectionCheckBox.check()
        table.tableHeader().clickColumn(0)
        val firstName = table.cell(TableCell.row(7).column(0)).value()
        table.clickCells {
            clickCell(7, 1)
        }
        table.tableHeader().clickColumn(1)

        val movedCell = table.cell(firstName)

        table.checkCellsSelection {
            cellSelected(movedCell.row, 1)
            cellIsNotSelected(7, 1)
        }
    }

    @Test
    fun convertCellToColumn(): Unit = with(app.pageObject.jTablePanel) {
        rowSelectionCheckBox.check()
        columnSelectionCheckBox.check()
        table.clickCells {
            clickCell(7, 1)
        }
        rowSelectionCheckBox.uncheck()
        table.checkCellsSelection {
            rowsShouldNotBeSelected(rows = listOf(7), exceptColumns = listOf(1))
            columnsShouldBeSelected(columns = listOf(1))
        }
    }

    @Test
    fun convertColumnToCell(): Unit = with(app.pageObject.jTablePanel) {
        rowSelectionCheckBox.uncheck()
        columnSelectionCheckBox.check()
        table.clickCells {
            clickCell(7, 1)
        }
        rowSelectionCheckBox.check()
        table.checkCellsSelection {
            rowsShouldNotBeSelected(rows = listOf(7), exceptColumns = listOf(1))
            columnsShouldNotBeSelected(columns = listOf(1), exceptRows = listOf(7))
        }
    }

    @Test
    fun convertCellToRow(): Unit = with(app.pageObject.jTablePanel) {
        rowSelectionCheckBox.check()
        columnSelectionCheckBox.check()
        table.clickCells {
            clickCell(7, 1)
        }
        columnSelectionCheckBox.uncheck()
        table.checkCellsSelection {
            columnsShouldNotBeSelected(columns = listOf(1), exceptRows = listOf(7))
            rowsShouldBeSelected(rows = listOf(7))
        }
    }

    @Test
    fun convertRowToCell(): Unit = with(app.pageObject.jTablePanel) {
        rowSelectionCheckBox.check()
        columnSelectionCheckBox.uncheck()
        table.clickCells {
            clickCell(7, 1)
        }
        columnSelectionCheckBox.check()
        table.checkCellsSelection {
            rowsShouldNotBeSelected(rows = listOf(7), exceptColumns = listOf(1))
            columnsShouldNotBeSelected(columns = listOf(1), exceptRows = listOf(7))
        }
    }

    @Test
    fun unSelectWithCheckBoxes(): Unit = with(app.pageObject.jTablePanel) {
        rowSelectionCheckBox.check()
        columnSelectionCheckBox.check()
        table.clickCells {
            clickCell(7, 1)
        }
        rowSelectionCheckBox.uncheck()
        columnSelectionCheckBox.uncheck()
        table.checkCellsSelection {
            cellIsNotSelected(7, 1)
        }
    }

    @Test
    fun switchOnCheckBoxes(): Unit = with(app.pageObject.jTablePanel) {
        rowSelectionCheckBox.uncheck()
        columnSelectionCheckBox.uncheck()
        table.clickCells {
            clickCell(7, 1)
        }
        rowSelectionCheckBox.check()
        columnSelectionCheckBox.check()
        table.checkCellsSelection {
            cellSelected(7, 1)
        }
    }

    @Test
    fun lastCellSelectedWithHotKey(): Unit = with(app.pageObject.jTablePanel) {
        rowSelectionCheckBox.check()
        columnSelectionCheckBox.check()
        table.clickCells {
            clickCell(2, 1)
            pressingControl { pressingKey(KeyEvent.VK_A) }
        }
        table.checkCellsSelection {
            columnsShouldNotBeSelected(columns = listOf(0, 1, 2, 3, 4))
            columnsShouldNotBeSelected(
                    columns = listOf(5),
                    exceptRows = listOf(table.rowCount() - 1)
            )
        }
    }

    @Test
    fun lastColumnSelectedWithHotKey(): Unit = with(app.pageObject.jTablePanel) {
        rowSelectionCheckBox.uncheck()
        columnSelectionCheckBox.check()
        table.clickCells {
            clickCell(2, 1)
            pressingControl { pressingKey(KeyEvent.VK_A) }
        }
        table.checkCellsSelection {
            columnsShouldNotBeSelected(columns = listOf(0, 1, 2, 3, 4))
            columnsShouldBeSelected(columns = listOf(5))
        }
    }

    @Test
    fun lastRowSelectedWithHotKey(): Unit = with(app.pageObject.jTablePanel) {
        rowSelectionCheckBox.check()
        columnSelectionCheckBox.uncheck()
        table.clickCells {
            clickCell(2, 1)
            pressingControl { pressingKey(KeyEvent.VK_A) }
        }
        table.checkCellsSelection {
            columnsShouldNotBeSelected(
                    columns = listOf(0, 1, 2, 3, 4),
                    exceptRows = listOf(table.rowCount() - 1))
            rowsShouldBeSelected(rows = listOf(table.rowCount() - 1))
        }
    }
}