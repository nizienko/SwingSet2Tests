package jTable

import engine.extensions.dragColumnToAnother
import engine.helpers.checkCellsSelection
import engine.helpers.clickCells
import org.fest.swing.data.TableCell
import org.junit.BeforeClass
import org.junit.Test
import swingSet2.SwingSet2
import java.awt.event.KeyEvent

class CellSelectionOneRangeModeTest : JTableTestSuite() {
    companion object {
        @JvmStatic
        @BeforeClass
        fun setSingleSelectionMode() {
            SwingSet2.jTablePanel.selectionModeComboBox.selectItem("One range")
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
            rowsShouldNotBeSelected(rows = listOf(1))
            columnsShouldNotBeSelected(columns = listOf(0))
        }
    }

    @Test
    fun onlyOneRangeRowSelectedPressingControl(): Unit = with(SwingSet2.jTablePanel) {
        rowSelectionCheckBox.check()
        columnSelectionCheckBox.uncheck()

        table.clickCells {
            cell(1, 0)
            pressingControl {
                cell(3, 4)
                cell(4, 3)
                cell(5, 2)
            }
        }
        table.checkCellsSelection {
            rowsShouldNotBeSelected(rows = listOf(1))
            rowsShouldBeSelected(rows = listOf(3, 4, 5))
            columnsShouldNotBeSelected(
                    columns = listOf(0, 2, 3, 4),
                    exceptRows = listOf(3, 4, 5)
            )
        }
    }

    @Test
    fun onlyOneRangeColumnsSelectedPressingShift(): Unit = with(SwingSet2.jTablePanel) {
        rowSelectionCheckBox.check()
        columnSelectionCheckBox.uncheck()

        table.clickCells {
            cell(1, 0)
            pressingShift {
                cell(3, 4)
                cell(9, 3)
                cell(5, 2)
            }
        }
        table.checkCellsSelection {
            rowsShouldNotBeSelected(rows = listOf(6, 7, 8, 9))
            rowsShouldBeSelected(rows = listOf(1, 2, 3, 4, 5))
            columnsShouldNotBeSelected(
                    columns = listOf(0, 2, 3, 4),
                    exceptRows = listOf(1, 2, 3, 4, 5)
            )
        }
    }

    @Test
    fun viceVersaSelectionPressingShift(): Unit = with(SwingSet2.jTablePanel) {
        rowSelectionCheckBox.check()
        columnSelectionCheckBox.uncheck()

        table.clickCells {
            cell(4, 0)
            pressingShift {
                cell(6, 4)
                cell(2, 3)
            }
        }
        table.checkCellsSelection {
            rowsShouldNotBeSelected(rows = listOf(5, 6))
            rowsShouldBeSelected(rows = listOf(2, 3, 4))
            columnsShouldNotBeSelected(
                    columns = listOf(0, 3, 4),
                    exceptRows = listOf(2, 3, 4)
            )
        }
    }

    @Test
    fun viceVersaSelectionPressingControl(): Unit = with(SwingSet2.jTablePanel) {
        rowSelectionCheckBox.check()
        columnSelectionCheckBox.uncheck()

        table.clickCells {
            cell(5, 0)
            pressingShift {
                cell(6, 4)
                cell(4, 3)
            }
        }
        table.checkCellsSelection {
            rowsShouldBeSelected(rows = listOf(4, 5))
            rowsShouldNotBeSelected(rows = listOf(6))
            columnsShouldNotBeSelected(
                    columns = listOf(0, 3, 4),
                    exceptRows = listOf(4, 5)
            )
        }
    }

    @Test
    fun sortingDescKeepsRowSelection(): Unit = with(SwingSet2.jTablePanel) {
        rowSelectionCheckBox.check()
        columnSelectionCheckBox.uncheck()

        table.tableHeader().clickColumn(0)
        val firstName = table.cell(TableCell.row(7).column(0)).value()

        table.clickCells {
            cell(7, 1)
            pressingShift {
                cell(10, 2)
            }
        }
        table.tableHeader().clickColumn(0)

        val movedCell = table.cell(firstName)

        table.checkCellsSelection {
            with(movedCell) {
                rowsShouldBeSelected(
                        rows = listOf(row, row - 1, row - 2, row - 3)
                )
            }
            rowsShouldNotBeSelected(rows = listOf(7, 8, 9, 10))
        }
    }

    @Test
    fun sortingResetRowSelection(): Unit = with(SwingSet2.jTablePanel) {
        rowSelectionCheckBox.check()
        columnSelectionCheckBox.uncheck()

        table.tableHeader().clickColumn(0)
        val firstName = table.cell(TableCell.row(7).column(0)).value()

        table.clickCells {
            cell(7, 1)
            pressingShift {
                cell(10, 2)
            }
        }
        table.tableHeader().clickColumn(1)

        val movedCell = table.cell(firstName)

        table.checkCellsSelection {
            rowsShouldBeSelected(rows = listOf(movedCell.row))
            columnsShouldNotBeSelected(
                    columns = listOf(0, 1, 2),
                    exceptRows = listOf(movedCell.row))
        }
    }

    @Test
    fun onlyOneRangeColumnsSelectedPressingControl(): Unit = with(SwingSet2.jTablePanel) {
        rowSelectionCheckBox.uncheck()
        columnSelectionCheckBox.check()

        table.clickCells {
            cell(1, 0)
            pressingControl {
                cell(3, 2)
                cell(4, 3)
                cell(5, 4)
            }
        }
        table.checkCellsSelection {
            columnsShouldBeSelected(columns = listOf(2, 3, 4))
            columnsShouldNotBeSelected(columns = listOf(0))
            rowsShouldNotBeSelected(
                    rows = listOf(1, 3, 4, 5),
                    exceptColumns = listOf(2, 3, 4)
            )
        }
    }

    @Test
    fun onlyOneRangeRowSelectedPressingShift(): Unit = with(SwingSet2.jTablePanel) {
        rowSelectionCheckBox.uncheck()
        columnSelectionCheckBox.check()

        table.clickCells {
            cell(1, 0)
            pressingShift {
                cell(3, 2)
                cell(9, 5)
                cell(5, 3)
            }
        }
        table.checkCellsSelection {
            columnsShouldBeSelected(columns = listOf(0, 1, 2, 3))
            columnsShouldNotBeSelected(columns = listOf(4, 5))
        }
    }

    @Test
    fun reorderingNeighborsKeepsColumnsSelection(): Unit = with(SwingSet2.jTablePanel) {
        rowSelectionCheckBox.uncheck()
        columnSelectionCheckBox.check()
        reorderingAllowedCheckBox.check()

        table.clickCells {
            cell(27, 1)
            pressingControl {
                cell(15, 2)
            }
        }
        table.dragColumnToAnother(fromIndex = 2, toIndex = 1)
        table.checkCellsSelection {
            columnsShouldBeSelected(columns = listOf(1, 2))
        }
    }

    @Test
    fun reorderingNotNeighborsLostColumnsSelection(): Unit = with(SwingSet2.jTablePanel) {
        rowSelectionCheckBox.uncheck()
        columnSelectionCheckBox.check()
        reorderingAllowedCheckBox.check()

        table.clickCells {
            cell(7, 1)
            pressingControl {
                cell(5, 2)
            }
        }
        table.dragColumnToAnother(fromIndex = 2, toIndex = 3)
        table.checkCellsSelection {
            columnsShouldBeSelected(columns = listOf(3))
            columnsShouldNotBeSelected(columns = listOf(1, 2))
        }
    }

    @Test
    fun injectionBetweenSelectedLostColumnsSelection(): Unit = with(SwingSet2.jTablePanel) {
        rowSelectionCheckBox.uncheck()
        columnSelectionCheckBox.check()
        reorderingAllowedCheckBox.check()

        table.clickCells {
            cell(7, 1)
            pressingControl {
                cell(5, 2)
            }
        }
        table.dragColumnToAnother(fromIndex = 3, toIndex = 2)
        table.checkCellsSelection {
            columnsShouldBeSelected(columns = listOf(1))
            columnsShouldNotBeSelected(columns = listOf(2, 3))
        }
    }

    @Test
    fun selectRowsWithHotKey(): Unit = with(SwingSet2.jTablePanel) {
        rowSelectionCheckBox.check()
        columnSelectionCheckBox.uncheck()
        table.clickCells {
            cell(2, 1)
            pressingControl { pressingKey(KeyEvent.VK_A) }
        }
        table.checkCellsSelection {
            for (n in 0 until table.rowCount()) {
                rowsShouldBeSelected(rows = listOf(n))
            }
        }
    }

    @Test
    fun selectColumnsWithHotKey(): Unit = with(SwingSet2.jTablePanel) {
        rowSelectionCheckBox.uncheck()
        columnSelectionCheckBox.check()
        table.clickCells {
            cell(2, 1)
            pressingControl { pressingKey(KeyEvent.VK_A) }
        }
        table.checkCellsSelection {
            columnsShouldBeSelected(columns = listOf(0, 1, 2, 3, 4, 5))
        }
    }

    @Test
    fun selectCellsWithHotKey(): Unit = with(SwingSet2.jTablePanel) {
        rowSelectionCheckBox.check()
        columnSelectionCheckBox.check()
        table.clickCells {
            cell(2, 1)
            pressingControl { pressingKey(KeyEvent.VK_A) }
        }
        table.checkCellsSelection {
            columnsShouldBeSelected(columns = listOf(0, 1, 2, 3, 4, 5))
        }
    }

    @Test
    fun cellsWithHotKeyCantSelectedWhenBothCheckboxesSwitchedOff(): Unit = with(SwingSet2.jTablePanel) {
        rowSelectionCheckBox.uncheck()
        columnSelectionCheckBox.uncheck()
        table.clickCells {
            cell(2, 1)
            pressingControl { pressingKey(KeyEvent.VK_A) }
        }
        table.checkCellsSelection {
            columnsShouldNotBeSelected(columns = listOf(0, 1, 2, 3, 4, 5))
        }
    }

    @Test
    fun onlyOneRangeCellsSelectedPressingControl(): Unit = with(SwingSet2.jTablePanel) {
        rowSelectionCheckBox.check()
        columnSelectionCheckBox.check()

        table.clickCells {
            cell(1, 0)
            pressingControl {
                cell(3, 2)
                cell(3, 3)
                cell(4, 2)
            }
        }
        table.checkCellsSelection {
            rowsShouldNotBeSelected(rows = listOf(1))
            rowsShouldNotBeSelected(
                    rows = listOf(3, 4),
                    exceptColumns = listOf(2, 3)
            )
            columnsShouldNotBeSelected(
                    columns = listOf(2, 3),
                    exceptRows = listOf(3, 4)
            )
            columnsShouldNotBeSelected(columns = listOf(0))
        }
    }

    @Test
    fun onlyOneRangeCellsSelectedPressingShiftAndControl(): Unit = with(SwingSet2.jTablePanel) {
        rowSelectionCheckBox.check()
        columnSelectionCheckBox.check()

        table.clickCells {
            cell(1, 0)
            pressingControl {
                cell(15, 0)
            }
            pressingShift {
                cell(17, 3)
            }
            pressingControl {
                cell(18, 2)
            }
        }
        table.checkCellsSelection {
            rowsShouldNotBeSelected(rows = listOf(1))
            rowsShouldNotBeSelected(
                    rows = listOf(15, 16, 17, 18),
                    exceptColumns = listOf(0, 1, 2, 3)
            )
            columnsShouldNotBeSelected(
                    columns = listOf(0, 1, 2, 3),
                    exceptRows = listOf(15, 16, 17, 18)
            )
        }
    }

    @Test
    fun unSelectionPartOfSelectedCellsWithControl(): Unit = with(SwingSet2.jTablePanel) {
        rowSelectionCheckBox.check()
        columnSelectionCheckBox.check()

        table.clickCells {
            cell(1, 0)
            pressingControl {
                cell(15, 0)
            }
            pressingShift {
                cell(17, 3)
            }
            pressingControl {
                cell(17, 0)
            }
        }
        table.checkCellsSelection {
            rowsShouldNotBeSelected(rows = listOf(1))
            rowsShouldNotBeSelected(
                    rows = listOf(15, 16),
                    exceptColumns = listOf(1, 2, 3)
            )
            columnsShouldNotBeSelected(
                    columns = listOf(1, 2, 3),
                    exceptRows = listOf(15, 16)
            )
        }
    }
}