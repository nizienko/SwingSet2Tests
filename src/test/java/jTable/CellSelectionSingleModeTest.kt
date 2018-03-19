package jTable

import engine.extensions.dragColumnToAnother
import engine.helpers.checkCellsSelection
import engine.helpers.clickCells
import org.fest.swing.data.TableCell
import org.junit.BeforeClass
import org.junit.Test
import swingSet2.SwingSet2
import java.awt.event.KeyEvent

class CellSelectionSingleModeTest : JTableTestSuite() {

    companion object {
        @JvmStatic
        @BeforeClass
        fun setSingleSelectionMode() {
            SwingSet2.jTablePanel.selectionModeComboBox.selectItem("Single")
        }
    }


    @Test
    fun noSelectedCellsWhenComboBoxesUnchecked(): Unit = with(SwingSet2.jTablePanel) {
        rowSelectionCheckBox.uncheck()
        columnSelectionCheckBox.uncheck()

        table.clickCells {
            cell(1, 0)
        }
        table.checkCellsSelection {
            cellIsNotSelected(1, 0)
        }
    }

    @Test
    fun onlyOneColumnSelectedWhenColumnComboBoxChecked(): Unit = with(SwingSet2.jTablePanel) {
        rowSelectionCheckBox.uncheck()
        columnSelectionCheckBox.check()

        table.clickCells {
            cell(7, 1)
            pressingControl {
                cell(9, 3)
            }
        }
        table.checkCellsSelection {
            columnsShouldNotBeSelected(columns = listOf(1))
            columnsShouldBeSelected(columns = listOf(3))
            rowsShouldNotBeSelected(rows = listOf(9), exceptColumns = listOf(3))
        }
    }

    @Test
    fun onlyOneColumnFromRangeSelectedWhenColumnComboBoxChecked(): Unit = with(SwingSet2.jTablePanel) {
        rowSelectionCheckBox.uncheck()
        columnSelectionCheckBox.check()

        table.clickCells {
            cell(7, 1)
            pressingShift {
                cell(9, 3)
            }
        }
        table.checkCellsSelection {
            columnsShouldNotBeSelected(columns = listOf(1, 2))
            columnsShouldBeSelected(columns = listOf(3))
            rowsShouldNotBeSelected(rows = listOf(7, 8, 9), exceptColumns = listOf(3))
        }
    }

    @Test
    fun columnUnSelectedWithSecondClickWhenColumnComboBoxChecked(): Unit = with(SwingSet2.jTablePanel) {
        rowSelectionCheckBox.uncheck()
        columnSelectionCheckBox.check()

        table.clickCells {
            cell(9, 5)
            pressingControl {
                cell(9, 5)
            }
        }
        table.checkCellsSelection {
            columnsShouldNotBeSelected(columns = listOf(5))
        }
    }

    @Test
    fun columnUnSelectedWithSecondClickOnColumn(): Unit = with(SwingSet2.jTablePanel) {
        rowSelectionCheckBox.uncheck()
        columnSelectionCheckBox.check()

        table.clickCells {
            cell(1, 3)
            pressingControl {
                cell(15, 3)
            }
        }
        table.checkCellsSelection {
            columnsShouldNotBeSelected(columns = listOf(3))
        }
    }

    @Test
    fun reorderingKeepsColumnSelection(): Unit = with(SwingSet2.jTablePanel) {
        rowSelectionCheckBox.uncheck()
        columnSelectionCheckBox.check()
        reorderingAllowedCheckBox.check()

        table.clickCells {
            cell(7, 1)
        }
        table.dragColumnToAnother(fromIndex = 1, toIndex = 4)
        table.checkCellsSelection {
            columnsShouldNotBeSelected(columns = listOf(1))
            columnsShouldBeSelected(columns = listOf(4))
            rowsShouldNotBeSelected(rows = listOf(7), exceptColumns = listOf(4))
        }
    }

    @Test
    fun reorderingFromAnotherColumnKeepsColumnSelection(): Unit = with(SwingSet2.jTablePanel) {
        rowSelectionCheckBox.uncheck()
        columnSelectionCheckBox.check()
        reorderingAllowedCheckBox.check()

        table.clickCells {
            cell(7, 1)
        }
        table.dragColumnToAnother(fromIndex = 4, toIndex = 1)
        table.checkCellsSelection {
            columnsShouldNotBeSelected(listOf(4, 1))
            columnsShouldBeSelected(columns = listOf(2))
            rowsShouldNotBeSelected(rows = listOf(7), exceptColumns = listOf(2))
        }
    }


    @Test
    fun sortingKeepsColumnSelection(): Unit = with(SwingSet2.jTablePanel) {
        rowSelectionCheckBox.uncheck()
        columnSelectionCheckBox.check()
        table.tableHeader().clickColumn(0)

        table.clickCells {
            cell(7, 1)
        }
        table.tableHeader().clickColumn(1)

        table.checkCellsSelection {
            columnsShouldBeSelected(columns = listOf(1))
        }
    }

    @Test
    fun sortingByAnotherColumnKeepsColumnSelection(): Unit = with(SwingSet2.jTablePanel) {
        rowSelectionCheckBox.uncheck()
        columnSelectionCheckBox.check()
        table.tableHeader().clickColumn(0)

        table.clickCells {
            cell(7, 1)
        }
        table.tableHeader().clickColumn(5)

        table.checkCellsSelection {
            columnsShouldBeSelected(columns = listOf(1))
        }
    }

    @Test
    fun onlyOneRowSelectedWhenRowComboBoxChecked(): Unit = with(SwingSet2.jTablePanel) {
        rowSelectionCheckBox.check()
        columnSelectionCheckBox.uncheck()

        table.clickCells {
            cell(7, 1)
            pressingControl {
                cell(9, 3)
            }
        }
        table.checkCellsSelection {
            rowsShouldNotBeSelected(rows = listOf(7))
            rowsShouldBeSelected(rows = listOf(9))
        }
    }

    @Test
    fun onlyOneRowFromRangeSelectedWhenRowComboBoxChecked(): Unit = with(SwingSet2.jTablePanel) {
        rowSelectionCheckBox.check()
        columnSelectionCheckBox.uncheck()

        table.clickCells {
            cell(7, 1)
            pressingShift {
                cell(9, 3)
            }
        }
        table.checkCellsSelection {
            rowsShouldNotBeSelected(rows = listOf(7, 8))
            rowsShouldBeSelected(rows = listOf(9))
            columnsShouldNotBeSelected(columns = listOf(3), exceptRows = listOf(9))
        }
    }

    @Test
    fun rowUnSelectedWithSecondClickWhenRowComboBoxChecked(): Unit = with(SwingSet2.jTablePanel) {
        rowSelectionCheckBox.check()
        columnSelectionCheckBox.uncheck()

        table.clickCells {
            cell(9, 3)
            pressingControl {
                cell(9, 3)
            }
        }
        table.checkCellsSelection {
            rowsShouldNotBeSelected(rows = listOf(9))
        }
    }

    @Test
    fun rowUnSelectedWithSecondClickOnRow(): Unit = with(SwingSet2.jTablePanel) {
        rowSelectionCheckBox.check()
        columnSelectionCheckBox.uncheck()

        table.clickCells {
            cell(9, 3)
            pressingControl {
                cell(9, 4)
            }
        }
        table.checkCellsSelection {
            rowsShouldNotBeSelected(rows = listOf(9))
        }
    }

    @Test
    fun reorderingKeepsRowSelection(): Unit = with(SwingSet2.jTablePanel) {
        rowSelectionCheckBox.check()
        columnSelectionCheckBox.uncheck()
        reorderingAllowedCheckBox.check()

        table.clickCells {
            cell(7, 1)
        }
        table.dragColumnToAnother(fromIndex = 1, toIndex = 4)
        table.checkCellsSelection {
            rowsShouldBeSelected(rows = listOf(7))
        }
    }

    @Test
    fun sortingKeepsRowSelection(): Unit = with(SwingSet2.jTablePanel) {
        rowSelectionCheckBox.check()
        columnSelectionCheckBox.uncheck()
        table.tableHeader().clickColumn(0)
        val firstName = table.cell(TableCell.row(7).column(0)).value()
        table.clickCells {
            cell(7, 1)
        }
        table.tableHeader().clickColumn(1)

        val movedCell = table.cell(firstName)

        table.checkCellsSelection {
            rowsShouldBeSelected(rows = listOf(movedCell.row))
            columnsShouldNotBeSelected(columns = listOf(movedCell.column), exceptRows = listOf(movedCell.row))
        }
    }

    @Test
    fun onlyOneCellSelectedWhenBothComboBoxesChecked(): Unit = with(SwingSet2.jTablePanel) {
        rowSelectionCheckBox.check()
        columnSelectionCheckBox.check()

        table.clickCells {
            cell(7, 1)
            pressingControl {
                cell(9, 3)
            }
        }
        table.checkCellsSelection {
            cellIsNotSelected(7, 1)
            rowsShouldNotBeSelected(rows = listOf(9), exceptColumns = listOf(3))
            columnsShouldNotBeSelected(columns = listOf(3), exceptRows = listOf(9))
        }
    }

    @Test
    fun onlyOneCellFromRangeSelectedWhenBothComboBoxesChecked(): Unit = with(SwingSet2.jTablePanel) {
        rowSelectionCheckBox.check()
        columnSelectionCheckBox.check()

        table.clickCells {
            cell(7, 1)
            pressingShift {
                cell(9, 3)
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
    fun cellUnSelectedWithSecondClick(): Unit = with(SwingSet2.jTablePanel) {
        rowSelectionCheckBox.check()
        columnSelectionCheckBox.check()

        table.clickCells {
            cell(9, 5)
            pressingControl {
                cell(9, 5)
            }
        }
        table.checkCellsSelection {
            cellIsNotSelected(9, 5)
        }
    }


    @Test
    fun reorderingKeepsCellSelection(): Unit = with(SwingSet2.jTablePanel) {
        rowSelectionCheckBox.check()
        columnSelectionCheckBox.check()
        reorderingAllowedCheckBox.check()

        table.clickCells {
            cell(7, 1)
        }
        table.dragColumnToAnother(fromIndex = 1, toIndex = 4)
        table.checkCellsSelection {
            cellIsNotSelected(7, 1)
            cellSelected(7, 4)
        }
    }

    @Test
    fun reorderingFromAnotherColumnKeepsCellSelection(): Unit = with(SwingSet2.jTablePanel) {
        rowSelectionCheckBox.check()
        columnSelectionCheckBox.check()
        reorderingAllowedCheckBox.check()

        table.clickCells {
            cell(7, 1)
        }
        table.dragColumnToAnother(fromIndex = 4, toIndex = 1)
        table.checkCellsSelection {
            cellIsNotSelected(7, 1)
            cellSelected(7, 2)
        }
    }

    @Test
    fun sortingKeepsCellSelection(): Unit = with(SwingSet2.jTablePanel) {
        rowSelectionCheckBox.check()
        columnSelectionCheckBox.check()
        table.tableHeader().clickColumn(0)
        val firstName = table.cell(TableCell.row(7).column(0)).value()
        table.clickCells {
            cell(7, 1)
        }
        table.tableHeader().clickColumn(1)

        val movedCell = table.cell(firstName)

        table.checkCellsSelection {
            cellSelected(movedCell.row, 1)
            cellIsNotSelected(7, 1)
        }
    }

    @Test
    fun convertCellToColumn(): Unit = with(SwingSet2.jTablePanel) {
        rowSelectionCheckBox.check()
        columnSelectionCheckBox.check()
        table.clickCells {
            cell(7, 1)
        }
        rowSelectionCheckBox.uncheck()
        table.checkCellsSelection {
            rowsShouldNotBeSelected(rows = listOf(7), exceptColumns = listOf(1))
            columnsShouldBeSelected(columns = listOf(1))
        }
    }

    @Test
    fun convertColumnToCell(): Unit = with(SwingSet2.jTablePanel) {
        rowSelectionCheckBox.uncheck()
        columnSelectionCheckBox.check()
        table.clickCells {
            cell(7, 1)
        }
        rowSelectionCheckBox.check()
        table.checkCellsSelection {
            rowsShouldNotBeSelected(rows = listOf(7), exceptColumns = listOf(1))
            columnsShouldNotBeSelected(columns = listOf(1), exceptRows = listOf(7))
        }
    }

    @Test
    fun convertCellToRow(): Unit = with(SwingSet2.jTablePanel) {
        rowSelectionCheckBox.check()
        columnSelectionCheckBox.check()
        table.clickCells {
            cell(7, 1)
        }
        columnSelectionCheckBox.uncheck()
        table.checkCellsSelection {
            columnsShouldNotBeSelected(columns = listOf(1), exceptRows = listOf(7))
            rowsShouldBeSelected(rows = listOf(7))
        }
    }

    @Test
    fun convertRowToCell(): Unit = with(SwingSet2.jTablePanel) {
        rowSelectionCheckBox.check()
        columnSelectionCheckBox.uncheck()
        table.clickCells {
            cell(7, 1)
        }
        columnSelectionCheckBox.check()
        table.checkCellsSelection {
            rowsShouldNotBeSelected(rows = listOf(7), exceptColumns = listOf(1))
            columnsShouldNotBeSelected(columns = listOf(1), exceptRows = listOf(7))
        }
    }

    @Test
    fun unSelectWithCheckBoxes(): Unit = with(SwingSet2.jTablePanel) {
        rowSelectionCheckBox.check()
        columnSelectionCheckBox.check()
        table.clickCells {
            cell(7, 1)
        }
        rowSelectionCheckBox.uncheck()
        columnSelectionCheckBox.uncheck()
        table.checkCellsSelection {
            cellIsNotSelected(7, 1)
        }
    }

    @Test
    fun switchOnCheckBoxes(): Unit = with(SwingSet2.jTablePanel) {
        rowSelectionCheckBox.uncheck()
        columnSelectionCheckBox.uncheck()
        table.clickCells {
            cell(7, 1)
        }
        rowSelectionCheckBox.check()
        columnSelectionCheckBox.check()
        table.checkCellsSelection {
            cellSelected(7, 1)
        }
    }

    @Test
    fun lastCellSelectedWithHotKey(): Unit = with(SwingSet2.jTablePanel) {
        rowSelectionCheckBox.check()
        columnSelectionCheckBox.check()
        table.clickCells {
            cell(2, 1)
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
    fun lastColumnSelectedWithHotKey(): Unit = with(SwingSet2.jTablePanel) {
        rowSelectionCheckBox.uncheck()
        columnSelectionCheckBox.check()
        table.clickCells {
            cell(2, 1)
            pressingControl { pressingKey(KeyEvent.VK_A) }
        }
        table.checkCellsSelection {
            columnsShouldNotBeSelected(columns = listOf(0, 1, 2, 3, 4))
            columnsShouldBeSelected(columns = listOf(5))
        }
    }

    @Test
    fun lastRowSelectedWithHotKey(): Unit = with(SwingSet2.jTablePanel) {
        rowSelectionCheckBox.check()
        columnSelectionCheckBox.uncheck()
        table.clickCells {
            cell(2, 1)
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