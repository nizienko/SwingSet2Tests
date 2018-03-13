package jTable

import engine.extensions.dragColumnToAnother
import engine.helpers.checkCellsSelection
import engine.helpers.clickCells
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
        }
    }

    @Test
    fun columnUnSelectedWithSecondClickWhenColumnComboBoxChecked(): Unit = with(SwingSet2.jTablePanel) {
        rowSelectionCheckBox.uncheck()
        columnSelectionCheckBox.check()

        table.clickCells {
            cell(9, 3)
            pressingControl {
                cell(9, 3)
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
        }
    }

}