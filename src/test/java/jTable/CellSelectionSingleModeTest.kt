package jTable

import engine.extensions.dragColumnToAnother
import engine.helpers.checkCellsSelection
import engine.helpers.clickCells
import org.fest.swing.data.TableCell
import org.junit.After
import org.junit.BeforeClass
import org.junit.Test
import swingSet2.SwingSet2

class CellSelectionSingleModeTest : JTableTestSuite() {

    companion object {
        @JvmStatic
        @BeforeClass
        fun setSingleSelectionMode() {
            SwingSet2.jTablePanel.selectionModeComboBox.selectItem("Single")
        }
    }

    @After
    fun removeAllSelections(): Unit = with(SwingSet2.jTablePanel) {
        rowSelectionCheckBox.check()
        columnSelectionCheckBox.check()
        table.clickCells {
            cell(1, 0)
            pressingControl {
                cell(1, 0)
            }
        }
        rowSelectionCheckBox.uncheck()
        columnSelectionCheckBox.uncheck()
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
            columnIsNotSelected(1)
            columnSelected(3)
            rowIsNotSelected(9, exceptCellAtColumn = 3)
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
            columnIsNotSelected(1)
            columnIsNotSelected(2)
            columnSelected(3)
            rowIsNotSelected(7, exceptCellAtColumn = 3)
            rowIsNotSelected(8, exceptCellAtColumn = 3)
            rowIsNotSelected(9, exceptCellAtColumn = 3)
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
            columnIsNotSelected(5)
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
            columnIsNotSelected(3)
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
            columnIsNotSelected(1)
            columnSelected(4)
            rowIsNotSelected(7, exceptCellAtColumn = 4)
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
            columnIsNotSelected(4)
            columnIsNotSelected(1)
            columnSelected(2)
            rowIsNotSelected(7, exceptCellAtColumn = 2)
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
            columnSelected(1)
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
            columnSelected(1)
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
            rowIsNotSelected(7)
            rowSelected(9)
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
            rowIsNotSelected(7)
            rowIsNotSelected(8)
            rowSelected(9)
            columnIsNotSelected(3, exceptCellAtRow = 9)
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
            rowIsNotSelected(9)
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
            rowIsNotSelected(9)
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
            rowSelected(7)
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
            rowSelected(movedCell.row)
            columnIsNotSelected(movedCell.column, exceptCellAtRow = movedCell.row)
        }
    }
}