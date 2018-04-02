package jTable.cellSelection

import engine.extensions.dragColumnToAnother
import engine.helpers.checkCellsSelection
import engine.helpers.clickCells
import jTable.JTableTestSuite
import org.junit.BeforeClass
import org.junit.Test
import swingSet2.SwingSet2
import java.awt.event.KeyEvent

class CellSelectionMultipleRangesTest : JTableTestSuite() {
    companion object {
        @JvmStatic
        @BeforeClass
        fun setSingleSelectionMode() {
            SwingSet2.jTablePanel.selectionModeComboBox.selectItem("Multiple ranges")
        }
    }

    @Test
    fun noSelectedCellsWhenComboBoxesUnchecked(): Unit = with(SwingSet2.jTablePanel) {
        rowSelectionCheckBox.uncheck()
        columnSelectionCheckBox.uncheck()

        table.clickCells {
            clickCell(1, 0)
        }
        table.checkCellsSelection {
            rowsShouldNotBeSelected(rows = listOf(1))
            columnsShouldNotBeSelected(columns = listOf(0))
        }
    }

    @Test
    fun multipleRowsSelectedPressingControl(): Unit = with(SwingSet2.jTablePanel) {
        rowSelectionCheckBox.check()
        columnSelectionCheckBox.uncheck()

        table.clickCells {
            clickCell(1, 0)
            pressingControl {
                clickCell(3, 4)
                clickCell(5, 3)
                clickCell(6, 2)
                clickCell(7, 1)

            }
        }
        table.checkCellsSelection {
            rowsShouldBeSelected(rows = listOf(1, 3, 5, 6, 7))
            columnsShouldNotBeSelected(
                    columns = listOf(0, 1, 2, 3, 4),
                    exceptRows = listOf(1, 3, 5, 6, 7)
            )
        }
    }

    @Test
    fun multipleRowsSelectedPressingShift(): Unit = with(SwingSet2.jTablePanel) {
        rowSelectionCheckBox.check()
        columnSelectionCheckBox.uncheck()

        table.clickCells {
            clickCell(1, 0)
            pressingShift {
                clickCell(3, 4)
                clickCell(5, 3)
                clickCell(6, 2)
                clickCell(7, 1)

            }
        }
        table.checkCellsSelection {
            rowsShouldBeSelected(rows = listOf(1, 2, 3, 4, 5, 6, 7))
            columnsShouldNotBeSelected(
                    columns = listOf(0, 1, 2, 3, 4),
                    exceptRows = listOf(1, 2, 3, 4, 5, 6, 7)
            )
        }
    }

    @Test
    fun multipleRowRangesSelectedPressingControlAndShift(): Unit = with(SwingSet2.jTablePanel) {
        rowSelectionCheckBox.check()
        columnSelectionCheckBox.uncheck()

        table.clickCells {
            clickCell(6, 0)
            pressingShift {
                clickCell(5, 4)
                clickCell(10, 3)
            }
            pressingControl {
                clickCell(15, 2)
                clickCell(16, 1)
                clickCell(17, 2)
                clickCell(18, 1)
            }
        }
        table.checkCellsSelection {
            rowsShouldBeSelected(
                    rows = listOf(6, 7, 8, 9, 10, 15, 16, 17, 18)
            )
            rowsShouldNotBeSelected(
                    rows = listOf(5, 11, 12, 13, 14, 19)
            )
        }
    }


    @Test
    fun unselectMiddleRows(): Unit = with(SwingSet2.jTablePanel) {
        rowSelectionCheckBox.check()
        columnSelectionCheckBox.uncheck()

        table.clickCells {
            clickCell(0, 0)
            pressingControl { pressingKey(KeyEvent.VK_A) }
            pressingControl {
                clickCell(17, 2)
                clickCell(14, 5)
                clickCell(13, 5)
                clickCell(12, 5)
                clickCell(13, 0)
            }
        }
        table.checkCellsSelection {
            columnsShouldBeSelected(
                    columns = listOf(0, 1, 2, 3, 4, 5),
                    exceptRows = listOf(17, 14, 12)
            )
        }
    }

    @Test
    fun sortingShouldKeepSelection(): Unit = with(SwingSet2.jTablePanel) {
        rowSelectionCheckBox.check()
        columnSelectionCheckBox.uncheck()

        table.tableHeader().clickColumn(1)

        table.clickCells {
            clickCell(10, 0)
            pressingShift {
                clickCell(16, 4)
            }
        }
        with(table.tableHeader()) {
            clickColumn(0)
            clickColumn(1)
        }

        table.checkCellsSelection {
            rowsShouldBeSelected(rows = listOf(10, 11, 12, 13, 14, 15, 16))
            columnsShouldNotBeSelected(
                    columns = listOf(0, 4),
                    exceptRows = listOf(10, 11, 12, 13, 14, 15, 16)
            )
        }
    }

    @Test
    fun multipleColumnSelectedPressingControlAndShift(): Unit = with(SwingSet2.jTablePanel) {
        rowSelectionCheckBox.uncheck()
        columnSelectionCheckBox.check()

        table.clickCells {
            clickCell(6, 0)
            pressingShift {
                clickCell(25, 2)
            }
            pressingControl {
                clickCell(15, 4)
                clickCell(16, 5)
            }
        }
        table.checkCellsSelection {
            columnsShouldBeSelected(
                    columns = listOf(0, 1, 2, 4, 5)
            )
            columnsShouldNotBeSelected(
                    columns = listOf(3)
            )
        }
    }

    @Test
    fun unselectMiddleColumns(): Unit = with(SwingSet2.jTablePanel) {
        rowSelectionCheckBox.uncheck()
        columnSelectionCheckBox.check()

        table.clickCells {
            clickCell(0, 0)
            pressingControl { pressingKey(KeyEvent.VK_A) }
            pressingControl {
                clickCell(17, 2)
                clickCell(14, 4)
                clickCell(13, 5)
                clickCell(12, 5)
            }
        }
        table.checkCellsSelection {
            columnsShouldBeSelected(
                    columns = listOf(0, 1, 3, 5)
            )
            columnsShouldNotBeSelected(
                    columns = listOf(2, 4)
            )
        }
    }

    @Test
    fun reorderingKeepsColumnsSelection(): Unit = with(SwingSet2.jTablePanel) {
        rowSelectionCheckBox.uncheck()
        columnSelectionCheckBox.check()
        reorderingAllowedCheckBox.check()

        table.clickCells {
            clickCell(27, 0)
            pressingShift {
                clickCell(15, 3)
            }
        }
        table.dragColumnToAnother(fromIndex = 2, toIndex = 4)
        table.checkCellsSelection {
            columnsShouldBeSelected(columns = listOf(0, 1, 2, 4))
        }
    }

    @Test
    fun multipleRangeCellsSelectedPressingShiftAndControl(): Unit = with(SwingSet2.jTablePanel) {
        rowSelectionCheckBox.check()
        columnSelectionCheckBox.check()

        table.clickCells {
            clickCell(10, 1)
            pressingShift {
                clickCell(15, 3)
            }
            pressingControl {
                clickCell(17, 3)
                clickCell(18, 3)
            }

        }
        table.checkCellsSelection {
            rowsShouldBeSelected(
                    rows = listOf(10, 11, 12, 13, 14, 15, 17, 18),
                    exceptColumns = listOf(0, 4, 5)
            )
            rowsShouldNotBeSelected(
                    rows = listOf(9, 16, 19)
            )
        }
    }
}